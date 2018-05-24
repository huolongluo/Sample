package com.example.huolongluo.sample.widget;

import android.content.Context;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.EditText;

import com.example.huolongluo.sample.util.L;

/**
 * 限制输入的EditText
 */
public class FilterEditText extends EditText
{
    //输入表情前的光标位置
    private int cursorPos;
    //输入表情前EditText中的文本
    private String inputAfterText;
    //是否重置了EditText的内容
    private boolean resetText;

    private String filter;
    private boolean isSupportChinese;

    private Context mContext;
    private boolean onlySupport;
    private String allowString;

    private String showText = "";

    public FilterEditText(Context context)
    {
        super(context);
        this.mContext = context;
        initEditText();
    }

    public FilterEditText(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        this.mContext = context;
        initEditText();
    }

    public FilterEditText(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initEditText();
    }

    public FilterEditText isSupportChinese(boolean isSupportChinese)
    {
        this.isSupportChinese = isSupportChinese;
        return this;
    }

    public FilterEditText setCustomFilter(String filter)
    {
        this.filter = filter;
        return this;
    }

    public FilterEditText setOnlySupportCharAndNum(boolean onlySupport)
    {
        this.onlySupport = onlySupport;
        return this;
    }

    public FilterEditText setAllow(String allowString)
    {
        this.allowString = allowString;
        return this;
    }

    // 初始化edittext 控件
    private void initEditText()
    {
        addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int before, int count)
            {
                if (!resetText)
                {
                    cursorPos = getSelectionEnd();
                    // 这里用s.toString()而不直接用s是因为如果用s，
                    // 那么，inputAfterText和s在内存中指向的是同一个地址，s改变了，
                    // inputAfterText也就改变了，那么表情过滤就失败了
                    inputAfterText = s.toString();
                }

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if (!resetText)
                {
                    L.d("count - before" + (count - before));
                    if (count - before >= 2)
                    {//表情符号的字符长度最小为2
                        CharSequence input = s.subSequence(start + before, start + count);
                        if (containsEmoji(input.toString()))
                        {
                            resetText();
                            showText = showText + "不支持输入Emoji表情符号";
                        }
                    }
                    if (count - before >= 0)
                    {
                        CharSequence input = s.subSequence(start + before, start + count);
                        if (!isSupportChinese)
                        {
                            if (containsChinese(input.toString()))
                            {
                                resetText();
                                showText = showText + " 不支持输入中文";
                            }
                        }

                        if (!TextUtils.isEmpty(filter))
                        {
                            if (customFilter(input.toString()))
                            {
                                resetText();
                                showText = showText + " 不支持输入 " + filter;
                            }
                        }

                        if (onlySupport)
                        {
                            if (!isOnly(input.toString()))
                            {
                                String text = " 只支持输入大小写字母/数字";
                                if (isSupportChinese)
                                {
                                    text = text + "/中文";
                                }

                                if (!TextUtils.isEmpty(allowString))
                                {
                                    text = text + allowString;
                                }

                                resetText();
                                showText = showText + text;
                            }
                        }
                    }

                    if (!showText.isEmpty())
                    {
//                        ToastSimple.show(showText, 3);
                        showText = "";
                    }
                }
                else
                {
                    resetText = false;
                }
            }

            @Override
            public void afterTextChanged(Editable editable)
            {

            }
        });
    }

    private void resetText()
    {
        resetText = true;
        //是表情符号就将文本还原为输入表情符号之前的内容
        setText(inputAfterText);
        CharSequence text = getText();
        if (text instanceof Spannable)
        {
            Spannable spanText = (Spannable) text;
            Selection.setSelection(spanText, text.length());
        }
    }


    /**
     * 检测是否有emoji表情
     *
     * @param source
     * @return
     */
    private boolean containsEmoji(String source)
    {
        int len = source.length();
        for (int i = 0; i < len; i++)
        {
            char codePoint = source.charAt(i);
            if (!isEmojiCharacter(codePoint))
            { //如果不能匹配,则该字符是Emoji表情
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否是Emoji
     *
     * @param codePoint 比较的单个字符
     * @return
     */
    private boolean isEmojiCharacter(char codePoint)
    {
        return (codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA) || (codePoint == 0xD) || ((codePoint >= 0x20) && (codePoint <= 0xD7FF)) || (
                (codePoint >= 0xE000) && (codePoint <= 0xFFFD)) || ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
    }

    private boolean containsChinese(String source)
    {
        int len = source.length();
        for (int i = 0; i < len; i++)
        {
            char codePoint = source.charAt(i);
            if (isChinese(codePoint))
            { // 该字符是中文
                return true;
            }
        }
        return false;
    }


    /**
     * 判定输入汉字
     *
     * @param c
     * @return
     */
    private boolean isChinese(char c)
    {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        return ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS || ub == Character
                .UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION || ub == Character.UnicodeBlock
                .CJK_SYMBOLS_AND_PUNCTUATION || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS;
    }

    /**
     * 不允许输入的字符
     *
     * @param source
     * @return
     */
    private boolean customFilter(String source)
    {
        int len = source.length();
        for (int i = 0; i < len; i++)
        {
            String codePoint = source.substring(i, i + 1);

            if (filter.contains(codePoint))
            { // 
                return true;
            }
        }
        return false;
    }

    /**
     * 只允许输入 大小写字母/数字
     *
     * @param source
     * @return
     */
    private boolean isOnly(String source)
    {
        StringBuilder sb = new StringBuilder();
        sb.append("[a-zA-Z0-9");

        if (isSupportChinese)
        {
            sb.append("\u4e00-\u9fa5");
        }

        if (!TextUtils.isEmpty(allowString))
        {
            sb.append(allowString);
        }

        sb.append("]+");

        int len = source.length();
        for (int i = 0; i < len; i++)
        {
            String codePoint = source.substring(i, i + 1);
            if (codePoint.matches(sb.toString()))
            {
                return true;
            }
        }
        return false;
    }
}