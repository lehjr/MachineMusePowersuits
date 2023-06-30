package net.machinemuse.numina.common.string;

import net.machinemuse.numina.client.render.MuseRenderer;
import net.minecraft.util.ResourceLocation;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class MuseStringUtils {
    /*
        http://www.petervis.com/electronics/SI_Prefix_Metric_System/pico_nano_micro_milli_Kilo_Mega_Giga_Tera.html

        Prefix	Symbol	Decimal Multiplier
        deca	da	10
        hecto	h	100
        kilo	k	1 000
        mega	M	1 000 000
        giga	G	1 000 000 000
        tera	T	1 000 000 000 000
        peta	P	1 000 000 000 000 000
        exa	    E	1 000 000 000 000 000 000
        zetta	Z	1 000 000 000 000 000 000 000
        yotta	Y	1 000 000 000 000 000 000 000 000

        deci	d	0.1
        centi	c	0.01
        milli	m	0.001
        micro	µ	0.000 001
        nano	n	0.000 000 001
        pico	p	0.000 000 000 001
        femto	f	0.000 000 000 000 001
        atto	a	0.000 000 000 000 000 001
        zepto	z	0.000 000 000 000 000 000 001
        yocto	y	0.000 000 000 000 000 000 000 001

*/
    // milli, micro, etc.
    public static final char[] smallSuffixes = {'m', 'μ', 'n', 'p', 'f', 'a', 'z', 'y'};
    public static final char[] bigSuffixes = {'k', 'M', 'G', 'T', 'P', 'E', 'Z', 'Y'};

    /**
     * Takes a number and outputs a string formatted in the way used by most of
     * the mod, 33.5k for example
     *
     * @param number
     * @return string
     */
    public static String formatNumberShort(double number) {
        DecimalFormat format = (DecimalFormat) DecimalFormat.getInstance();
        format.setMinimumIntegerDigits(1);
        format.setMaximumFractionDigits(2);
        format.applyPattern("##0.##E0");

        String[] exploded = format.format(number).split("E");
        String retval = exploded[0];
        if (retval.length() > 3) {
            if (retval.charAt(3) == '.') {
                retval = retval.substring(0, 3);
            } else {
                retval = retval.substring(0, 4);
            }
        }
        if (exploded.length > 1) {
            int exponent = Integer.parseInt(exploded[1]);
            int index;
            if (exponent > 0) {
                index = exponent / 3 - 1;
                if (index > bigSuffixes.length - 1)
                    retval = "Infinite M";
                else
                    retval += bigSuffixes[index];
            } else if (exponent < 0) {
                index = exponent / -3 - 1;
                retval += smallSuffixes[index];
            }
        }
        return retval;
    }

    /**
     * Takes a number and formats it as a %. e.g. 1.13%, 22.4%, 100%
     *
     * @param number
     * @return
     */
    public static String formatNumberPercent(double number) {
        DecimalFormat format = (DecimalFormat) DecimalFormat.getInstance();
        format.setMinimumIntegerDigits(1);
        format.setMaximumFractionDigits(2);
        format.applyPattern("##0.##");
        return format.format(number * 100);
    }

    /**
     * Picks the correct function according to the units.
     *
     * @param number
     * @param units
     * @return
     */
    public static String formatNumberFromUnits(double number, String units) {
        if (units.equals("%")) {
            return formatNumberPercent(number) + '%';
        } else {
            return formatNumberShort(number) + units;
        }
    }

    /**
     * Puts the associated format tag (italic, bold, green, etc.) before a
     * string.
     *
     * @param str
     * @param format
     * @return
     */
    public static String prependFormatTag(String str, char format) {
        return "\u00a7" + format + str;
    }

    /**
     * Puts the 'resetButton formatting' tag at the end of the string.
     *
     * @param str
     * @return
     */
    public static String appendResetTag(String str) {
        return str + "\u00a7r";
    }

    /**
     * Puts the format tag on the front and the resetButton tag on the end
     *
     * @param str
     * @param format
     * @return
     */
    public static String wrapFormatTags(String str, char format) {
        return appendResetTag(prependFormatTag(str, format));
    }

    /**
     * Puts the format tag on the front and the resetButton tag on the end (enum
     * version)
     *
     * @param str
     * @param code
     * @return
     */
    public static String wrapFormatTags(String str, FormatCodes code) {
        return wrapFormatTags(str, code.character);
    }

    /**
     * Takes multiple format tags and adds them all, then appends a resetButton tag.
     *
     * @param str
     * @param tags
     * @return
     */
    public static String wrapMultipleFormatTags(String str, Object... tags) {
        for (Object tag : tags) {
            if (tag instanceof Character) {
                str = prependFormatTag(str, (Character) tag);
            } else if (tag instanceof FormatCodes) {
                str = prependFormatTag(str, ((FormatCodes) tag).character);
            }
        }
        return appendResetTag(str);
    }

    /**
     * Takes a string and wraps it to a certain # of characters
     *
     * @param str
     * @param length
     * @return a list of strings which are no longer than
     * <p/>
     * <pre>
     *                         length
     *                         </pre>
     * <p>
     * unless there is a sequence of non-space characters longer than
     *
     * <pre>
     *                         length
     *                         </pre>
     */
    public static List<String> wrapStringToLength(String str, int length) {
        if (str == null)
            str = "";

        String[] stringArray = wordUtilsWrap(str, length);
        List<String> strlist = Arrays.asList(stringArray);
        return strlist;
    }

    // org.apache.commons.lang3.StringUtils.isBlank
    static boolean isBlank(final CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    // TODO: return List of strings
    // org.apache.commons.text.WordUtils
    static String[] wordUtilsWrap(final String str, int wrapLength) {
        if (str == null) {
            return null;
        }

        String newLineStr = System.lineSeparator();
        String wrapOn = " ";
        if (wrapLength < 1) {
            wrapLength = 1;
        }
        if (isBlank(wrapOn)) {
            wrapOn = " ";
        }

        final Pattern patternToWrapOn = Pattern.compile(wrapOn);
        final int inputLineLength = str.length();
        int offset = 0;

        final StringBuilder wrappedLine = new StringBuilder(inputLineLength + 32);

        while (offset < inputLineLength) {
            int spaceToWrapAt = -1;
            Matcher matcher = patternToWrapOn.matcher(str.substring(offset,
                    Math.min((int) Math.min(Integer.MAX_VALUE, offset + wrapLength + 1L), inputLineLength)));
            if (matcher.find()) {
                if (matcher.start() == 0) {
                    offset += matcher.end();
                    continue;
                }
                spaceToWrapAt = matcher.start() + offset;
            }

            // only last line without leading spaces is left
            if (inputLineLength - offset <= wrapLength) {
                break;
            }

            while (matcher.find()) {
                spaceToWrapAt = matcher.start() + offset;
            }

            if (spaceToWrapAt >= offset) {
                // normal case
                wrappedLine.append(str, offset, spaceToWrapAt);
                wrappedLine.append(newLineStr);
                offset = spaceToWrapAt + 1;

            } else {
                // do not wrap really long word, just extend beyond limit
                matcher = patternToWrapOn.matcher(str.substring(offset + wrapLength));
                if (matcher.find()) {
                    spaceToWrapAt = matcher.start() + offset + wrapLength;
                }

                if (spaceToWrapAt >= 0) {
                    wrappedLine.append(str, offset, spaceToWrapAt);
                    wrappedLine.append(newLineStr);
                    offset = spaceToWrapAt + 1;
                } else {
                    wrappedLine.append(str, offset, str.length());
                    offset = inputLineLength;
                }
            }
        }

        // Whatever is left in line is short enough to just pass through
        wrappedLine.append(str, offset, str.length());

        return wrappedLine.toString().split(System.lineSeparator());
    }


    /**
     * Takes a string and wraps it to a certain length using MuseRenderer's string length
     *
     * @param str
     * @param length
     * @return a list of strings which are no longer than
     * <p/>
     * <pre>
     *                         length
     *                         </pre>
     * <p>
     * unless there is a sequence of non-space characters longer than
     *
     * <pre>
     *                         length
     *                         </pre>
     */
    public static List<String> wrapStringToVisualLength(String str, double length) {
        List<String> strlist = new ArrayList<>();

        String[] words = str.split(" ");
        if (words.length == 0) {
            return null;
        }

        String currLine = words[0];
        for (int i = 1; i < words.length; i++) {
            String approxLine = currLine + " " + words[i];
            if (MuseRenderer.getStringWidth(approxLine) > length) {
                strlist.add(currLine);
                currLine = " " + words[i];
            } else {
                currLine = approxLine;
            }
        }
        strlist.add(currLine);

        return strlist;
    }

    public static String extractName(ResourceLocation resource) {
        String filename = resource.toString();
        int ix = Math.max(filename.lastIndexOf('/'), Math.max(filename.lastIndexOf('\\'), filename.lastIndexOf(':'))) + 1;
        return filename.substring(ix, filename.lastIndexOf('.')).trim();
    }

    public static String extractName(String filename) {
        int ix = Math.max(filename.lastIndexOf('/'), Math.max(filename.lastIndexOf('\\'), filename.lastIndexOf(':'))) + 1;
        return filename.substring(ix, filename.lastIndexOf('.')).trim();
    }

    /**
     * Enum of format codes used by the vanilla Minecraft font renderer
     *
     * @author MachineMuse
     */
    public enum FormatCodes {
        Black('0'),
        DarkBlue('1'),
        DarkGreen('2'),
        DarkAqua('3'),
        DarkRed('4'),
        Purple('5'),
        Gold('6'),
        Grey('7'),
        DarkGrey('8'),
        Indigo('9'),
        BrightGreen('a'),
        Aqua('b'),
        Red('c'),
        Pink('d'),
        Yellow('e'),
        White('f'),
        RandomChar('k'),
        Bold('l'),
        Strike('m'),
        Underlined('n'),
        Italic('o'),
        Reset('r');

        public final char character;

        FormatCodes(char character) {
            this.character = character;
        }
    }
}
