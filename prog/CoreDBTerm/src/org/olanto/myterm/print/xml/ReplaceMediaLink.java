/**
 * ********
 * Copyright © 2013-2014 Olanto Foundation Geneva
 *
 * This file is part of myTERM.
 *
 * myCAT is free software: you can redistribute it and/or modify it under the
 * terms of the GNU Affero General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * myCAT is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Affero General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with myCAT. If not, see <http://www.gnu.org/licenses/>.
 *
 *********
 */
package org.olanto.myterm.print.xml;

/*
 * 
 how to include link:
 [[link/tuning mysql.txt]] --> <A href="documents/tuning mysql.txt" target="_blank">tuning mysql.txt</A>.
 [[link/tuning mysql.txt|optimise]] --> <A href="documents/tuning mysql.txt" target="_blank">optimise</A>.
 [[link!http://www.w3.org/]] --> <A href="http://www.w3.org/" target="_blank">http://www.w3.org/</A>.
 [[link!http://www.w3.org/|W3C Web site]] --> <A href="http://www.w3.org/" target="_blank">W3C Web site</A>.


 how to include images:
 [[images/volcan.jpg]] --> <IMG SRC="/images/volcan.jpg" width="200">
 [[images!http:localhost:8080/images/volcan.jpg]] --> <IMG SRC="http:localhost:8080/images/volcan.jpg"  width="200">
 [[images/volcan.jpg|parameters]] <IMG SRC="/images/volcan.jpg" parameters>
 [[images/volcan.jpg|width="120"]] <IMG SRC="/images/volcan.jpg" width="120">
 how to include sounds:
 [[sounds/sound.mp3]] --> <AUDIO src="/sounds/sound.mp3" autostart="false" controls="true">Your browser does not support the <code>audio</code> element.</audio>
 */
public class ReplaceMediaLink {

    public static final String MARK_BEGIN = "[[";
    public static final String MARK_END = "]]";
    public static final String MARK_PARAMETERS = "|";
    public static final String MARK_EXTERNAL = "!";
    public static final String MARK_ERROR = "Error";
    public static final String MEDIA_LINKS = "links";
    public static final String MEDIA_IMAGES = "images";
    public static final String MEDIA_SOUNDS = "sounds";
    public static final String MEDIA_VIDEOS = "videos";
    public static final String MEDIA_IMAGES_DEFAULT = "width=\"200\"";
    public static final String MEDIA_SOUNDS_DEFAULT = "autostart=\"false\" controls=\"true\"";
   public static final String MEDIA_VIDEOS_DEFAULT = " width=\"400\" height=\"300\" autostart=\"false\" controls=\"true\"";
   

    public static void main(String[] args) {
        String text = "<h1>Test virtual path</h1>\n"
                + "<h3>links</h3>\n"
                + "[[links/tuning mysql.txt]]\n" // --> <A href=\"documents/tuning mysql.txt\" target=\"_blank\">tuning mysql.txt</A>.
                + "[[links/tuning mysql.txt|optimise]]\n" // --> <A href=\"documents/tuning mysql.txt\" target=\"_blank\">optimise</A>.
                + "[[links!http://www.w3.org/]]\n" // --> <A href=\"http://www.w3.org/\" target=\"_blank\">http://www.w3.org/</A>.
                + "[[links!http://www.w3.org/|W3C Web site]]\n" // --> <A href=\"http://www.w3.org/\" target=\"_blank\">W3C Web site</A>."
                + "<h3>show picture</h3>\n"
                + "[[images/volcan.jpg]]\n"
                + "more 1...\n"
                + "[[images/volcan.jpg|width=\"120\"]]\n"
                + "more 2...\n"
                + "[[images!http://localhost/images/volcan.jpg]]\n"
                + "more 3 ..."
                + "[[images!http://localhost/images/volcan.jpg|width=\"120\"]]\n"
                + "<h3>play sounds</h3>\n"
                + "[[sounds/sound.mp3]]\n"
                + "more 1...\n"
                + "[[sounds/sound.mp3|autostart=\"true\" controls=\"true\"]]\n"
                + "more 2...\n"
                + "[[sounds!http://localhost/sounds/sound.mp3]]\n"
                + "more 3 ..."
                + "[[sounds!http://localhost/sounds/sound.mp3|controls=\"true\"]]\n"
                     + "<h3>play videos</h3>\n"
                + "[[videos/big_buck_bunny_720p_1mb.mp4]]\n"
                + "more 1...\n"
                + "[[videos/big_buck_bunny_720p_1mb.mp4|controls=\"true\"]]\n"
                + "more 2...\n"
                + "[[videos!http://localhost/videos/big_buck_bunny_720p_1mb.mp4]]\n"
                + "more 3 ..."
                + "[[videos!http://localhost/videos/big_buck_bunny_720p_1mb.mp4|controls=\"true\"]]\n"
                    ;
        System.out.println(replaceMediaLink(text));
        System.out.println("end");
    }

    public static String analyse(String s) {
        System.out.println("debug:"+s);
        s = s.substring(MARK_BEGIN.length(), s.length() - MARK_END.length());
        int startExternal = s.length();
        int startParameters = s.length();
        String external = null;
        String parameters = null;
        String media = null;
        if (s.contains(MARK_PARAMETERS)) {
            startParameters = s.indexOf(MARK_PARAMETERS);
            parameters = s.substring(startParameters + 1, s.length());

        }
        if (s.contains(MARK_EXTERNAL)) {
            startExternal = s.indexOf(MARK_EXTERNAL);
            external = s.substring(startExternal + 1, startParameters);
        }
        media = s.substring(0, startParameters);

        String newTag = buildTag(media, external, parameters);
        if (newTag.startsWith(MARK_ERROR)) {
            return "<<" + " " + newTag + " " + s + ">>";
        }
        return newTag;
    }

    public static String buildTag(String media, String external, String parameters) {
        String res = "";
        System.out.println("parameters:" + parameters);
        System.out.println("external:" + external);
        System.out.println("xmedia:" + media);
        if (media.startsWith(MEDIA_LINKS)) {
            System.out.println("media LINKS:" + media);
            String showtext = parameters;
            // <A href="/documents/tuning mysql.txt" target=\"_blank\">tuning mysql.txt</A>.
            if (parameters == null) {
                showtext = external;
                if (external == null) {
                    showtext = media.substring(MEDIA_LINKS.length());
                    if (showtext.startsWith("/")) {
                        showtext = showtext.substring(1);
                    }
                }
            }
            String url = external;
            if (external == null) {
                url = "/"+media;
            }
            res = "<MEDIA html:a xmlns:html=\"http://www.w3.org/1999/xhtml\"  href=\"" + url + "\" target=\"_blank\">MEDIA" + showtext + "<MEDIA /html:a>MEDIA";
            return res;
        }
        if (media.startsWith(MEDIA_IMAGES)) {
            System.out.println("media IMAGES:" + media);
            String showtext = parameters;
            // <IMG SRC="http:localhost:8080/images/volcan.jpg"  width="200">
            if (parameters == null) {
                parameters = MEDIA_IMAGES_DEFAULT;
            }
            String url = external;
            if (external == null) {
                url = "/"+media;
            }
            res = "<MEDIA html:img xmlns:html=\"http://www.w3.org/1999/xhtml\" src=\"" + url + "\" "+parameters + " />MEDIA";
            return res;
        }
        if (media.startsWith(MEDIA_SOUNDS)) {
            System.out.println("media SOUNDS:" + media);
            String showtext = parameters;
            // <audio src="http://localhost/sounds/sound.mp3" autostart="false" controls="true">
            // Your browser does not support the <code>audio</code> element.
            //</audio>
            if (parameters == null) {
                parameters = MEDIA_SOUNDS_DEFAULT;
            }
            String url = external;
            if (external == null) {
                url = "/"+media;
            }
            res = "<MEDIA html:audio xmlns:html=\"http://www.w3.org/1999/xhtml\" src=\"" + url + "\" "+parameters + " >MEDIA Your browser does not support the <MEDIA code>MEDIAaudio<MEDIA /code>MEDIA element.\n<MEDIA /html:audio>MEDIA";
            return res;
      }
        if (media.startsWith(MEDIA_VIDEOS)) {
            System.out.println("media VIDEOS:" + media);
            String showtext = parameters;
            // <audio src="http://localhost/sounds/sound.mp3" autostart="false" controls="true">
            // Your browser does not support the <code>audio</code> element.
            //</audio>
            if (parameters == null) {
                parameters = MEDIA_VIDEOS_DEFAULT;
            }
            String url = external;
            if (external == null) {
                url = "/"+media;
            }
            res = "<MEDIA html:video xmlns:html=\"http://www.w3.org/1999/xhtml\" src=\"" + url + "\" "+parameters + " />MEDIA";
            return res;
      }
        return MARK_ERROR;
    }

    public static String replaceMediaLink(String s) {
        int start = s.indexOf(MARK_BEGIN);
        if (start == -1) {
            //System.out.println("no [[");
            return s;
        }
        int stop = s.indexOf(MARK_END, start);
        if (stop == -1) {
            System.out.println("error no ]]" + s);
            return s;
        }
        String tobeSubstitute = s.substring(start, stop + 2);
        String byThis = analyse(tobeSubstitute);
        System.out.println(tobeSubstitute + " --> " + byThis);
        s = s.replace(tobeSubstitute, byThis);
        s = replaceMediaLink(s);
        return s;

    }
}
