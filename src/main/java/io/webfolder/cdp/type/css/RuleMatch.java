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
 * Match data for a CSS rule
 */
@UseStag
public class RuleMatch {
    private CSSRule rule;

    private List<Integer> matchingSelectors;

    /**
     * CSS rule in the match.
     */
    public CSSRule getRule() {
        return rule;
    }

    /**
     * CSS rule in the match.
     */
    public void setRule(CSSRule rule) {
        this.rule = rule;
    }

    /**
     * Matching selector indices in the rule's selectorList selectors (0-based).
     */
    public List<Integer> getMatchingSelectors() {
        return matchingSelectors;
    }

    /**
     * Matching selector indices in the rule's selectorList selectors (0-based).
     */
    public void setMatchingSelectors(List<Integer> matchingSelectors) {
        this.matchingSelectors = matchingSelectors;
    }
}
