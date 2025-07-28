package com.minigod.zero.dbs.util.pgp;

import org.apache.commons.io.IOUtils;
import org.bouncycastle.bcpg.ArmoredOutputStream;
import org.bouncycastle.bcpg.CompressionAlgorithmTags;
import org.bouncycastle.bcpg.HashAlgorithmTags;
import org.bouncycastle.bcpg.SymmetricKeyAlgorithmTags;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openpgp.*;
import org.bouncycastle.openpgp.operator.PBESecretKeyDecryptor;
import org.bouncycastle.openpgp.operator.bc.*;
import org.bouncycastle.openpgp.operator.jcajce.JcePGPDataEncryptorBuilder;
import org.bouncycastle.openpgp.operator.jcajce.JcePublicKeyKeyEncryptionMethodGenerator;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchProviderException;
import java.security.Provider;
import java.security.SecureRandom;
import java.security.Security;
import java.util.Date;
import java.util.Iterator;

public class BCPGPEncryptor {

    private final static String UTF_8 = "UTF-8";

    private static Provider getProvider() {
		Provider provider = Security.getProvider("BC");
		if (provider == null) {
			provider = new BouncyCastleProvider();
			Security.addProvider(provider);
		}
		return provider;
	}

	Encrypt encrypt = new Encrypt();

	public BCPGPEncryptor(Encrypt encrypt) throws IOException, PGPException, NoSuchProviderException {
		encrypt.setPublicKey(BCPGPUtils.readPublicKey(encrypt.getPublicKeyFilePath()));

		PGPSecretKey secretKey = BCPGPUtils.readSecretKey(encrypt.getPrivateKeyFilePath());
		encrypt.setSecretKey(secretKey);

		BcPGPDigestCalculatorProvider pgpDigestCalculatorProvider = new BcPGPDigestCalculatorProvider();
		String privateKeyPassword = encrypt.getPrivateKeyPassword();
		char[] charArray = privateKeyPassword.toCharArray();
		BcPBESecretKeyDecryptorBuilder bcPBESecretKeyDecryptorBuilder = new BcPBESecretKeyDecryptorBuilder(pgpDigestCalculatorProvider);
		PBESecretKeyDecryptor build = bcPBESecretKeyDecryptorBuilder.build(charArray);
		PGPPrivateKey privateKey = secretKey
				.extractPrivateKey(build);
		encrypt.setPrivateKey(privateKey);
		this.encrypt = encrypt;
	}

	public void encryptFile(String inputFileNamePath, String outputFileNamePath) throws Exception {
		if (encrypt.isSigning()) {
			encryptAndSignFile(inputFileNamePath, outputFileNamePath, encrypt.getPublicKeyFilePath(),
					encrypt.getPrivateKeyFilePath(), encrypt.getPrivateKeyPassword());
		} else {
			encryptFile(inputFileNamePath, outputFileNamePath, encrypt.getPublicKeyFilePath(), encrypt.isArmored(),
					encrypt.isCheckIntegrity());
		}
		System.err.println(IOUtils.toString(new FileInputStream(outputFileNamePath)));
	}

	public String encryptMessage(String message) throws PGPException, IOException {
		if (encrypt.isSigning()) {
			final ByteArrayOutputStream encryptedMessage = new ByteArrayOutputStream();
            InputStream inputStream = IOUtils.toInputStream(message, StandardCharsets.UTF_8);
			byte[] encryptedMsgByte = encryptAndSignMessage(toByteArray(inputStream), encrypt.getPublicKeyFilePath(),
					encrypt.getPrivateKeyFilePath(), encrypt.getPrivateKeyPassword());
			final String encryptedMsg = new String(encryptedMsgByte);
			return encryptedMsg;
		} else {
			return "Without Signing not supported";
		}
	}

    public byte[] toByteArray(InputStream input) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024*4];
        int n = 0;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
        }
        return output.toByteArray();
    }

	public void encryptFile(String inputFile, String outputFile, String publicKeyFile, boolean armor,
			boolean withIntegrityCheck) throws IOException, NoSuchProviderException, PGPException {
		getProvider();

		OutputStream out = null;
		InputStream keyStream = null;
		OutputStream cOut = null;
		try {
			keyStream = new FileInputStream(publicKeyFile);
			PGPPublicKey pubKey = BCPGPUtils.readPublicKey(keyStream);
			out = new FileOutputStream(outputFile);
			if (armor) {
				out = new ArmoredOutputStream(out);
			}

			ByteArrayOutputStream bOut = new ByteArrayOutputStream();
			PGPCompressedDataGenerator comData = new PGPCompressedDataGenerator(CompressionAlgorithmTags.ZIP);
			PGPUtil.writeFileToLiteralData(comData.open(bOut), PGPLiteralData.BINARY, new File(inputFile, UTF_8));
			comData.close();

			byte[] compressedData = bOut.toByteArray();
			JcePGPDataEncryptorBuilder dataEncryptor = new JcePGPDataEncryptorBuilder(PGPEncryptedData.AES_256);
			dataEncryptor.setWithIntegrityPacket(withIntegrityCheck);
			dataEncryptor.setSecureRandom(new SecureRandom());

			PGPEncryptedDataGenerator encGen = new PGPEncryptedDataGenerator(dataEncryptor);
			encGen.addMethod(new JcePublicKeyKeyEncryptionMethodGenerator(pubKey));
			cOut = encGen.open(out, compressedData.length);
			cOut.write(compressedData);
			cOut.close();

			if (armor) {
				out.close();
			}

		} finally {
			try {
				cOut.close();
			} catch (Exception e) {
			}
			try {
				out.close();
			} catch (Exception e) {
			}
			try {
				keyStream.close();
			} catch (Exception e) {
			}
		}
	}

	public void encryptAndSignFile(String inputFile, String outputFile, String publicKeyFile, String privateKeyFile,
			String passPhrase) {
		getProvider();
		InputStream input = null;
		OutputStream output = null;
		InputStream encryptKeyInput = null;
		File fileOutput = null;
		try {
			input = new FileInputStream(inputFile);
			fileOutput = new File(outputFile);
			output = new FileOutputStream(fileOutput);
			encryptKeyInput = new FileInputStream(publicKeyFile);

			int DEFAULT_BUFFER_SIZE = 16 * 1024;

			PGPSecretKey pgpSec = BCPGPUtils.readSecretKey(privateKeyFile);
			PGPPrivateKey signingKey = encrypt.getPrivateKey();

			String userid = (String) pgpSec.getPublicKey().getUserIDs().next();

			BcPGPDataEncryptorBuilder dataEncryptor = new BcPGPDataEncryptorBuilder(PGPEncryptedData.AES_256);
			dataEncryptor.setWithIntegrityPacket(encrypt.isCheckIntegrity());
			dataEncryptor.setSecureRandom(new SecureRandom());

			PGPEncryptedDataGenerator encryptedDataGenerator = new PGPEncryptedDataGenerator(dataEncryptor);
			encryptedDataGenerator.addMethod((new BcPublicKeyKeyEncryptionMethodGenerator(encrypt.getPublicKey())));

			OutputStream finalOut = new BufferedOutputStream(new ArmoredOutputStream(output), DEFAULT_BUFFER_SIZE);
			OutputStream encOut = encryptedDataGenerator.open(finalOut, new byte[DEFAULT_BUFFER_SIZE]);

			PGPCompressedDataGenerator compressedDataGenerator = new PGPCompressedDataGenerator(PGPCompressedData.ZIP);
			OutputStream compressedOut = new BufferedOutputStream(compressedDataGenerator.open(encOut));

			PGPSignatureGenerator signatureGenerator = new PGPSignatureGenerator(new BcPGPContentSignerBuilder(
					encrypt.getSecretKey().getPublicKey().getAlgorithm(), HashAlgorithmTags.SHA256));
			signatureGenerator.init(PGPSignature.BINARY_DOCUMENT, signingKey);

			PGPSignatureSubpacketGenerator subpacketGenerator = new PGPSignatureSubpacketGenerator();
			subpacketGenerator.setSignerUserID(false, userid);
			signatureGenerator.setHashedSubpackets(subpacketGenerator.generate());

			PGPOnePassSignature onePassSignature = signatureGenerator.generateOnePassVersion(false);
			onePassSignature.encode(compressedOut);

			PGPLiteralDataGenerator literalDataGenerator = new PGPLiteralDataGenerator(true);
			OutputStream literalOut = literalDataGenerator.open(compressedOut, PGPLiteralData.BINARY,
					fileOutput.getName(), new Date(), new byte[1 << 16]);

			byte[] buffer = new byte[1 << 16];

			int bytesRead = 0;
			while ((bytesRead = input.read(buffer)) != -1) {
				literalOut.write(buffer, 0, bytesRead);
				signatureGenerator.update(buffer, 0, bytesRead);
				literalOut.flush();
			}
			// Close Literal data stream and add signature
			literalOut.close();
			literalDataGenerator.close();
			signatureGenerator.generate().encode(compressedOut);
			// Close all other streams
			compressedOut.close();
			compressedDataGenerator.close();
			encOut.close();
			encryptedDataGenerator.close();
			finalOut.close();

			input.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				input.close();
			} catch (Exception e) {
			}
			try {
				output.close();
			} catch (Exception e) {
			}
			try {
				encryptKeyInput.close();
			} catch (Exception e) {
			}
		}
	}

	public byte[] encryptAndSignMessage(final byte[] message, String publicKeyFile, String privateKeyFile, String passPhrase)
			throws PGPException {

		getProvider();

		try {

			final ByteArrayOutputStream out = new ByteArrayOutputStream();
			final PGPEncryptedDataGenerator encryptedDataGenerator = new PGPEncryptedDataGenerator(
					new BcPGPDataEncryptorBuilder(SymmetricKeyAlgorithmTags.AES_256).setWithIntegrityPacket(true)
							.setSecureRandom(new SecureRandom()));

			encryptedDataGenerator.addMethod(new BcPublicKeyKeyEncryptionMethodGenerator(encrypt.getPublicKey())
					.setSecureRandom(new SecureRandom()));

			final OutputStream theOut = encrypt.isArmored() ? new ArmoredOutputStream(out) : out;
			final OutputStream encryptedOut = encryptedDataGenerator.open(theOut, new byte[4096]);

			final PGPCompressedDataGenerator compressedDataGenerator = new PGPCompressedDataGenerator(
					CompressionAlgorithmTags.ZIP);
			final OutputStream compressedOut = compressedDataGenerator.open(encryptedOut, new byte[4096]);

			PGPSecretKey pgpSec = BCPGPUtils.readSecretKey(privateKeyFile);
			PGPPrivateKey signingKey = encrypt.getPrivateKey();

			PGPSignatureGenerator signatureGenerator = new PGPSignatureGenerator(new BcPGPContentSignerBuilder(
					encrypt.getSecretKey().getPublicKey().getAlgorithm(), HashAlgorithmTags.SHA256));
			signatureGenerator.init(PGPSignature.BINARY_DOCUMENT, signingKey);

			final Iterator<?> it = pgpSec.getPublicKey().getUserIDs();
			if (it.hasNext()) {
				final PGPSignatureSubpacketGenerator spGen = new PGPSignatureSubpacketGenerator();
				spGen.setSignerUserID(false, (String) it.next());
				signatureGenerator.setHashedSubpackets(spGen.generate());
			}


			signatureGenerator.generateOnePassVersion(false).encode(compressedOut);

			final PGPLiteralDataGenerator literalDataGenerator = new PGPLiteralDataGenerator();
			final OutputStream literalOut = literalDataGenerator.open(compressedOut, PGPLiteralData.BINARY, "filename",
					new Date(), new byte[4096]);
			final InputStream in = new ByteArrayInputStream(message);
			final byte[] buf = new byte[4096];
			for (int len; (len = in.read(buf)) > 0;) {
				literalOut.write(buf, 0, len);
				signatureGenerator.update(buf, 0, len);
			}
			in.close();
			literalDataGenerator.close();
			signatureGenerator.generate().encode(compressedOut);
			compressedDataGenerator.close();
			encryptedDataGenerator.close();
			theOut.close();
			return out.toByteArray();
		} catch (Exception e) {
			throw new PGPException("Error in signAndEncrypt", e);
		}
	}

}
