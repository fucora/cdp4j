/**
 * cdp4j Commercial License
 *
 * Copyright 2017, 2019 WebFolder OÜ
 *
 * Permission  is hereby  granted,  to "____" obtaining  a  copy of  this software  and
 * associated  documentation files  (the "Software"), to deal in  the Software  without
 * restriction, including without limitation  the rights  to use, copy, modify,  merge,
 * publish, distribute  and sublicense  of the Software,  and to permit persons to whom
 * the Software is furnished to do so, subject to the following conditions:
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR  IMPLIED,
 * INCLUDING  BUT NOT  LIMITED  TO THE  WARRANTIES  OF  MERCHANTABILITY, FITNESS  FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL  THE AUTHORS  OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package io.webfolder.cdp.type.css;

import java.util.List;

import com.vimeo.stag.UseStag;

/**
 * Inherited CSS rule collection from ancestor node
 */
@UseStag
public class InheritedStyleEntry {
    private CSSStyle inlineStyle;

    private List<RuleMatch> matchedCSSRules;

    /**
     * The ancestor node's inline style, if any, in the style inheritance chain.
     */
    public CSSStyle getInlineStyle() {
        return inlineStyle;
    }

    /**
     * The ancestor node's inline style, if any, in the style inheritance chain.
     */
    public void setInlineStyle(CSSStyle inlineStyle) {
        this.inlineStyle = inlineStyle;
    }

    /**
     * Matches of CSS rules matching the ancestor node in the style inheritance chain.
     */
    public List<RuleMatch> getMatchedCSSRules() {
        return matchedCSSRules;
    }

    /**
     * Matches of CSS rules matching the ancestor node in the style inheritance chain.
     */
    public void setMatchedCSSRules(List<RuleMatch> matchedCSSRules) {
        this.matchedCSSRules = matchedCSSRules;
    }
}
