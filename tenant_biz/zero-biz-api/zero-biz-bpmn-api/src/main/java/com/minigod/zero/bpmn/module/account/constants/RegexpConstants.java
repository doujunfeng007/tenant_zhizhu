package com.minigod.zero.bpmn.module.account.constants;

/**
 * @ClassName: RegexpConstants
 * @Description: 常规正则
 * @Author chenyu
 * @Date 2022/9/7
 * @Version 1.0
 */
public interface RegexpConstants {
    String IS_AUDIO = ".(opus|flac|webm|weba|wav|ogg|m4a|mp3|oga|mid|amr|aiff|wma|au|aac)";
    String IS_AUDIO_URL = "^((http[s]{0,1})://).*.(opus|flac|webm|weba|wav|ogg|m4a|mp3|oga|mid|amr|aiff|wma|au|aac)";
    String IS_VIDEO = ".(mp4|avi|wmv|mpg|mpeg|mov|rm|ram|swf|flv)";
    String IS_VIDEO_URL = "^((http[s]{0,1})://).*.(mp4|avi|wmv|mpg|mpeg|mov|rm|ram|swf|flv)";
    String IS_IMAGE = ".(xbm|tif|pjp|svgz|jpg|jpeg|ico|tiff|gif|svg|jfif|webp|png|bmp|pjpeg|avif)";
    String IS_IMAGE_URL = "^((http[s]{0,1})://).*.(xbm|tif|pjp|svgz|jpg|jpeg|ico|tiff|gif|svg|jfif|webp|png|bmp|pjpeg|avif)";
    String STRONG_CIPHER = "^.{6,}$";
    String REGEX_LETTER_NUMBER = "";
    String REGEX_NUMBER = "^[A-Za-z0-9]+$";// 数字
    String REGEX_EMAIL = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$"; //邮箱
    String REGEX_ENG_NUMBER = "^[A-Za-z0-9]+$";// 英文数字
    String REGEX_ENG_NUMBER_DASH = "^[A-Za-z0-9|-]+$";// 英文数字-破折号
    String REGEX_NONE_ENG = "^[^a-zA-Z]+$";// 不含英文字母

}
