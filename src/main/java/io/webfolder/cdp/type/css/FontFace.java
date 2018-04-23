/**
 * cdp4j - Chrome DevTools Protocol for Java
 * Copyright © 2017, 2018 WebFolder OÜ (support@webfolder.io)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package io.webfolder.cdp.type.css;

/**
 * Properties of a web font: https://www
 * w3
 * org/TR/2008/REC-CSS2-20080411/fonts
 * html#font-descriptions
 */
public class FontFace {
    private String fontFamily;

    private String fontStyle;

    private String fontVariant;

    private String fontWeight;

    private String fontStretch;

    private String unicodeRange;

    private String src;

    private String platformFontFamily;

    /**
     * The font-family.
     */
    public String getFontFamily() {
        return fontFamily;
    }

    /**
     * The font-family.
     */
    public void setFontFamily(String fontFamily) {
        this.fontFamily = fontFamily;
    }

    /**
     * The font-style.
     */
    public String getFontStyle() {
        return fontStyle;
    }

    /**
     * The font-style.
     */
    public void setFontStyle(String fontStyle) {
        this.fontStyle = fontStyle;
    }

    /**
     * The font-variant.
     */
    public String getFontVariant() {
        return fontVariant;
    }

    /**
     * The font-variant.
     */
    public void setFontVariant(String fontVariant) {
        this.fontVariant = fontVariant;
    }

    /**
     * The font-weight.
     */
    public String getFontWeight() {
        return fontWeight;
    }

    /**
     * The font-weight.
     */
    public void setFontWeight(String fontWeight) {
        this.fontWeight = fontWeight;
    }

    /**
     * The font-stretch.
     */
    public String getFontStretch() {
        return fontStretch;
    }

    /**
     * The font-stretch.
     */
    public void setFontStretch(String fontStretch) {
        this.fontStretch = fontStretch;
    }

    /**
     * The unicode-range.
     */
    public String getUnicodeRange() {
        return unicodeRange;
    }

    /**
     * The unicode-range.
     */
    public void setUnicodeRange(String unicodeRange) {
        this.unicodeRange = unicodeRange;
    }

    /**
     * The src.
     */
    public String getSrc() {
        return src;
    }

    /**
     * The src.
     */
    public void setSrc(String src) {
        this.src = src;
    }

    /**
     * The resolved platform font family
     */
    public String getPlatformFontFamily() {
        return platformFontFamily;
    }

    /**
     * The resolved platform font family
     */
    public void setPlatformFontFamily(String platformFontFamily) {
        this.platformFontFamily = platformFontFamily;
    }
}
