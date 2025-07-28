package com.minigod.zero.dbs.util.pgp;

import org.apache.commons.io.IOUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openpgp.*;
import org.bouncycastle.openpgp.operator.bc.BcKeyFingerprintCalculator;
import org.bouncycastle.openpgp.operator.bc.BcPGPContentVerifierBuilderProvider;
import org.bouncycastle.openpgp.operator.bc.BcPublicKeyDataDecryptorFactory;
import org.bouncycastle.util.io.Streams;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchProviderException;
import java.security.Provider;
import java.security.Security;
import java.util.Iterator;

public class BCPGPDecryptor {

	int compressionAlgorithm;
	int hashAlgorithm;
	int symmetricKeyAlgorithm;

	Decrypt decrypt = new Decrypt();

	private static Provider getProvider() {
		Provider provider = Security.getProvider("BC");
		if (provider == null) {
			provider = new BouncyCastleProvider();
			Security.addProvider(provider);
		}
		return provider;
	}

	public BCPGPDecryptor(Decrypt decrypt) throws IOException, PGPException, NoSuchProviderException {
		this.decrypt = decrypt;
	}

	public void decryptFile(String inputFileNamePath, String outputFileNamePath) throws Exception {
		if (decrypt.isVerify()) {
			decryptandVerifyFile(inputFileNamePath, outputFileNamePath, decrypt.getPublicKeyFilePath(),
					decrypt.getPrivateKeyFilePath(), decrypt.getPrivateKeyPassword());
		} else {
			decryptFile(inputFileNamePath, outputFileNamePath, decrypt.getPrivateKeyFilePath(),
					decrypt.getPrivateKeyPassword());
		}
		System.err.println(IOUtils.toString(new FileInputStream(outputFileNamePath)));
	}

	public String decryptMessage(String encryptedMessage) throws Exception {
		byte[] decryptedMsgByte = null;
        InputStream inputStream = IOUtils.toInputStream(encryptedMessage, StandardCharsets.UTF_8);
		if (decrypt.isVerify()) {
			decryptedMsgByte = decryptandVerifyMessage(toByteArray(inputStream),
					decrypt.getPublicKeyFilePath(), decrypt.getPrivateKeyFilePath(), decrypt.getPrivateKeyPassword());
		} else {
			decryptedMsgByte = decryptMessage(toByteArray(inputStream), decrypt.getPrivateKeyFilePath(),
					decrypt.getPrivateKeyPassword());
		}
		final String decryptedMsg = new String(decryptedMsgByte);

		return decryptedMsg;
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

	public void decryptFile(String inputFile, String outputFile, String privateKeyFile, String passphrase)
			throws Exception {
		getProvider();
		InputStream fIn = null;
		InputStream in = null;
		InputStream keyIn = null;
		OutputStream out = null;
		try {
			char[] passPhrase = passphrase.toCharArray();
			keyIn = new FileInputStream(privateKeyFile);
			fIn = new FileInputStream(inputFile);
			out = new FileOutputStream(outputFile);
			in = PGPUtil.getDecoderStream(fIn);

			PGPObjectFactory pgpF = new PGPObjectFactory(in, new BcKeyFingerprintCalculator());
			PGPEncryptedDataList enc = null;
			Object o = pgpF.nextObject();

			if (o instanceof PGPEncryptedDataList) {
				enc = (PGPEncryptedDataList) o;
			} else {
				enc = (PGPEncryptedDataList) pgpF.nextObject();
			}

			Iterator<PGPPublicKeyEncryptedData> it = enc.getEncryptedDataObjects();
			PGPPrivateKey sKey = null;
			PGPPublicKeyEncryptedData pbe = null;

			while (sKey == null && it.hasNext()) {
				pbe = it.next();
				sKey = BCPGPUtils.findPrivateKey(keyIn, pbe.getKeyID(), passPhrase);
			}

			InputStream clear = pbe.getDataStream(new BcPublicKeyDataDecryptorFactory(sKey));
			this.symmetricKeyAlgorithm = pbe.getSymmetricAlgorithm(new BcPublicKeyDataDecryptorFactory(sKey));

			PGPObjectFactory pgpFact = new PGPObjectFactory(clear, new BcKeyFingerprintCalculator());

			o = pgpFact.nextObject();
			if (o instanceof PGPCompressedData) {
				PGPCompressedData cData = (PGPCompressedData) o;
				pgpFact = new PGPObjectFactory(cData.getDataStream(), new BcKeyFingerprintCalculator());
				o = pgpFact.nextObject();
				this.compressionAlgorithm = cData.getAlgorithm();
			}

			if (o instanceof PGPLiteralData) {
				PGPLiteralData ld = (PGPLiteralData) o;
				InputStream unc = ld.getInputStream();
				OutputStream fOut = new BufferedOutputStream(new FileOutputStream(outputFile));
				Streams.pipeAll(unc, fOut);
				fOut.close();
			}

			if (pbe.isIntegrityProtected()) {
				if (!pbe.verify()) {
					System.err.println("<-----message failed integrity check------->");
				} else {
					System.out.println("<-----message integrity check passed------->");
				}
			} else {
				System.out.println("<--------no message integrity check------>");
			}

		} finally {
			try {
				fIn.close();
			} catch (Exception e) {
			}
			try {
				in.close();
			} catch (Exception e) {
			}
			try {
				out.close();
			} catch (Exception e) {
			}
			try {
				keyIn.close();
			} catch (Exception e) {
			}
		}
	}

	public void decryptandVerifyFile(String inputFile, String outFile, String publicKeyFile, String privateKeyFile,
			String passPhrase) {
		getProvider();
		InputStream input = null;
		InputStream verifyKeyInput = null;
		OutputStream output = null;
		InputStream decryptKeyInput = null;
		char[] passwd = null;
		try {
			passwd = passPhrase.toCharArray();
			input = PGPUtil.getDecoderStream(new FileInputStream(inputFile));
			verifyKeyInput = new FileInputStream(privateKeyFile);
			output = new FileOutputStream(outFile);
			decryptKeyInput = new FileInputStream(publicKeyFile);

			PGPObjectFactory pgpF = new PGPObjectFactory(input, new BcKeyFingerprintCalculator());
			PGPEncryptedDataList enc;

			Object o = pgpF.nextObject();

			if (o instanceof PGPEncryptedDataList) {
				enc = (PGPEncryptedDataList) o;
			} else {
				enc = (PGPEncryptedDataList) pgpF.nextObject();
			}

			Iterator<?> it = enc.getEncryptedDataObjects();
			PGPPrivateKey sKey = null;
			PGPPublicKeyEncryptedData pbe = null;
			PGPSecretKeyRingCollection pgpSec = new PGPSecretKeyRingCollection(PGPUtil.getDecoderStream(verifyKeyInput),
					new BcKeyFingerprintCalculator());

			while (sKey == null && it.hasNext()) {
				pbe = (PGPPublicKeyEncryptedData) it.next();
				sKey = BCPGPUtils.findSecretKey(pgpSec, pbe.getKeyID(), passwd);
			}

			if (sKey == null) {
				throw new IllegalArgumentException("secret key for message not found.");
			}

			InputStream clear = pbe.getDataStream(new BcPublicKeyDataDecryptorFactory(sKey));
			this.symmetricKeyAlgorithm = pbe.getSymmetricAlgorithm(new BcPublicKeyDataDecryptorFactory(sKey));

			PGPObjectFactory plainFact = new PGPObjectFactory(clear, new BcKeyFingerprintCalculator());

			Object message = null;

			PGPOnePassSignatureList onePassSignatureList = null;
			PGPSignatureList signatureList = null;
			PGPCompressedData compressedData = null;

			message = plainFact.nextObject();
			ByteArrayOutputStream actualOutput = new ByteArrayOutputStream();

			while (message != null) {
				if (message instanceof PGPCompressedData) {
					compressedData = (PGPCompressedData) message;
					plainFact = new PGPObjectFactory(compressedData.getDataStream(), new BcKeyFingerprintCalculator());
					message = plainFact.nextObject();
					this.compressionAlgorithm = compressedData.getAlgorithm();
				}

				if (message instanceof PGPLiteralData) {
					Streams.pipeAll(((PGPLiteralData) message).getInputStream(), actualOutput);
				} else if (message instanceof PGPOnePassSignatureList) {
					onePassSignatureList = (PGPOnePassSignatureList) message;
				} else if (message instanceof PGPSignatureList) {
					signatureList = (PGPSignatureList) message;
				} else {
					System.err.println("<----message unknown message type---->");
				}
				message = plainFact.nextObject();
			}
			actualOutput.close();
			PGPPublicKey publicKey = null;
			byte[] outputBytes = actualOutput.toByteArray();
			if (onePassSignatureList == null || signatureList == null) {
				throw new PGPException("Poor PGP. Signatures not found.");
			} else {
				for (int i = 0; i < onePassSignatureList.size(); i++) {
					PGPOnePassSignature ops = onePassSignatureList.get(0);
					PGPPublicKeyRingCollection pgpRing = new PGPPublicKeyRingCollection(
							PGPUtil.getDecoderStream(decryptKeyInput), new BcKeyFingerprintCalculator());
					publicKey = pgpRing.getPublicKey(ops.getKeyID());
					if (publicKey != null) {
						ops.init(new BcPGPContentVerifierBuilderProvider(), publicKey);
						ops.update(outputBytes);
						PGPSignature signature = signatureList.get(i);
						if (ops.verify(signature)) {
							this.hashAlgorithm = ops != null ? ops.getHashAlgorithm() : 0;
							Iterator<?> userIds = publicKey.getUserIDs();
							while (userIds.hasNext()) {
								String userId = (String) userIds.next();
							}
						} else {
							System.err.println("<-------Signature verification failed------>");
						}
					}
				}

			}

			if (pbe.isIntegrityProtected() && !pbe.verify()) {
				System.err.println("Data is integrity protected but integrity is lost.");
			} else if (publicKey == null) {
				System.err.println("Signature not found");
			}

			output.write(outputBytes);
			output.flush();
			output.close();
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
				verifyKeyInput.close();
			} catch (Exception e) {
			}
			try {
				decryptKeyInput.close();
			} catch (Exception e) {
			}
		}
	}

	public byte[] decryptMessage(byte[] encryptedMessage, String privateKeyFile, String passphrase)
			throws IOException, PGPException, NoSuchProviderException {
		InputStream in = new ByteArrayInputStream(encryptedMessage);
		InputStream keyIn = null;
		try {
			in = PGPUtil.getDecoderStream(in);
			char[] passPhrase = passphrase.toCharArray();
			keyIn = new FileInputStream(privateKeyFile);

			PGPObjectFactory pgpF = new PGPObjectFactory(in, new BcKeyFingerprintCalculator());
			PGPEncryptedDataList enc;
			Object o = pgpF.nextObject();

			//
			// the first object might be a PGP marker packet.
			//
			if (o instanceof PGPEncryptedDataList) {
				enc = (PGPEncryptedDataList) o;
			} else {
				enc = (PGPEncryptedDataList) pgpF.nextObject();
			}

			Iterator<PGPPublicKeyEncryptedData> it = enc.getEncryptedDataObjects();
			PGPPrivateKey sKey = null;
			PGPPublicKeyEncryptedData pbe = null;

			while (sKey == null && it.hasNext()) {
				pbe = it.next();
				sKey = BCPGPUtils.findPrivateKey(keyIn, pbe.getKeyID(), passPhrase);
			}

			InputStream clear = pbe.getDataStream(new BcPublicKeyDataDecryptorFactory(sKey));
			this.symmetricKeyAlgorithm = pbe.getSymmetricAlgorithm(new BcPublicKeyDataDecryptorFactory(sKey));

			PGPObjectFactory pgpFact = new PGPObjectFactory(clear, new BcKeyFingerprintCalculator());

			o = pgpFact.nextObject();
			if (o instanceof PGPCompressedData) {
				PGPCompressedData cData = (PGPCompressedData) o;
				pgpFact = new PGPObjectFactory(cData.getDataStream(), new BcKeyFingerprintCalculator());
				o = pgpFact.nextObject();
				this.compressionAlgorithm = cData.getAlgorithm();
			}

			if (pbe.isIntegrityProtected()) {
				if (!pbe.verify()) {
					System.err.println("<-----message failed integrity check------->");
				} else {
					System.out.println("<-----message integrity check passed------->");
				}
			} else {
				System.out.println("<--------no message integrity check------>");
			}

			PGPLiteralData ld = (PGPLiteralData) pgpFact.nextObject();

			return Streams.readAll(ld.getInputStream());
		} finally {
			try {
				in.close();
			} catch (Exception e) {
			}

			try {
				keyIn.close();
			} catch (Exception e) {
			}
		}
	}

	public byte[] decryptandVerifyMessage(byte[] encryptedMessage, String publicKeyFile, String privateKeyFile,
			String passphrase) throws IOException, PGPException, NoSuchProviderException {
		InputStream in = new ByteArrayInputStream(encryptedMessage);
		InputStream keyIn = null;
		InputStream decryptKeyInput = null;
		try {
			in = PGPUtil.getDecoderStream(in);
			char[] passPhrase = passphrase.toCharArray();
			keyIn = new FileInputStream(privateKeyFile);
			decryptKeyInput = new FileInputStream(publicKeyFile);

			PGPObjectFactory pgpF = new PGPObjectFactory(in, new BcKeyFingerprintCalculator());
			PGPEncryptedDataList enc;
			Object o = pgpF.nextObject();

			//
			// the first object might be a PGP marker packet.
			//
			if (o instanceof PGPEncryptedDataList) {
				enc = (PGPEncryptedDataList) o;
			} else {
				enc = (PGPEncryptedDataList) pgpF.nextObject();
			}

			Iterator<PGPPublicKeyEncryptedData> it = enc.getEncryptedDataObjects();
			PGPPrivateKey sKey = null;
			PGPPublicKeyEncryptedData pbe = null;

			while (sKey == null && it.hasNext()) {
				pbe = it.next();
				sKey = BCPGPUtils.findPrivateKey(keyIn, pbe.getKeyID(), passPhrase);
			}

			if (sKey == null) {
				throw new IllegalArgumentException("secret key for message not found.");
			}

			InputStream clear = pbe.getDataStream(new BcPublicKeyDataDecryptorFactory(sKey));
			this.symmetricKeyAlgorithm = pbe.getSymmetricAlgorithm(new BcPublicKeyDataDecryptorFactory(sKey));

			PGPObjectFactory pgpFact = new PGPObjectFactory(clear, new BcKeyFingerprintCalculator());

			Object message = null;

			PGPOnePassSignatureList onePassSignatureList = null;
			PGPSignatureList signatureList = null;
			PGPCompressedData compressedData = null;

			message = pgpFact.nextObject();
			ByteArrayOutputStream actualOutput = new ByteArrayOutputStream();

			while (message != null) {
				if (message instanceof PGPCompressedData) {
					compressedData = (PGPCompressedData) message;
					pgpFact = new PGPObjectFactory(compressedData.getDataStream(), new BcKeyFingerprintCalculator());
					message = pgpFact.nextObject();
					this.compressionAlgorithm = compressedData.getAlgorithm();
				}

				if (message instanceof PGPLiteralData) {
					Streams.pipeAll(((PGPLiteralData) message).getInputStream(), actualOutput);
				} else if (message instanceof PGPOnePassSignatureList) {
					onePassSignatureList = (PGPOnePassSignatureList) message;
				} else if (message instanceof PGPSignatureList) {
					signatureList = (PGPSignatureList) message;
				} else {
					System.err.println("<----message unknown message type---->");
				}
				message = pgpFact.nextObject();
			}

			actualOutput.close();

			PGPPublicKey publicKey = null;
			byte[] outputBytes = actualOutput.toByteArray();
			if (onePassSignatureList == null || signatureList == null) {
				throw new PGPException("Poor PGP. Signatures not found.");
			} else {
				for (int i = 0; i < onePassSignatureList.size(); i++) {
					PGPOnePassSignature ops = onePassSignatureList.get(0);
					PGPPublicKeyRingCollection pgpRing = new PGPPublicKeyRingCollection(
							PGPUtil.getDecoderStream(decryptKeyInput), new BcKeyFingerprintCalculator());
					publicKey = pgpRing.getPublicKey(ops.getKeyID());
					if (publicKey != null) {
						ops.init(new BcPGPContentVerifierBuilderProvider(), publicKey);
						ops.update(outputBytes);
						PGPSignature signature = signatureList.get(i);
						if (ops.verify(signature)) {
							this.hashAlgorithm = ops != null ? ops.getHashAlgorithm() : 0;
							Iterator<?> userIds = publicKey.getUserIDs();
							while (userIds.hasNext()) {
								String userId = (String) userIds.next();
							}
						} else {
							System.err.println("<-------Signature verification failed------>");
						}
					}
				}

			}

			if (pbe.isIntegrityProtected() && !pbe.verify()) {
				System.err.println("Data is integrity protected but integrity is lost.");
			} else if (publicKey == null) {
				System.err.println("Signature not found");
			}

			return actualOutput.toByteArray();
		} finally {
			try {
				in.close();
			} catch (Exception e) {
			}

			try {
				keyIn.close();
			} catch (Exception e) {
			}
		}
	}
}
