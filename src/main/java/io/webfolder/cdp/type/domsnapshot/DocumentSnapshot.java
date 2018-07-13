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
package io.webfolder.cdp.type.domsnapshot;

/**
 * Document snapshot
 */
public class DocumentSnapshot {
    private Integer documentURL;

    private Integer baseURL;

    private Integer contentLanguage;

    private Integer encodingName;

    private Integer publicId;

    private Integer systemId;

    private Integer frameId;

    private NodeTreeSnapshot nodes;

    private LayoutTreeSnapshot layout;

    private TextBoxSnapshot textBoxes;

    /**
     * Document URL that <code>Document</code>or<code>FrameOwner</code> node points to.
     */
    public Integer getDocumentURL() {
        return documentURL;
    }

    /**
     * Document URL that <code>Document</code>or<code>FrameOwner</code> node points to.
     */
    public void setDocumentURL(Integer documentURL) {
        this.documentURL = documentURL;
    }

    /**
     * Base URL that <code>Document</code>or<code>FrameOwner</code> node uses for URL completion.
     */
    public Integer getBaseURL() {
        return baseURL;
    }

    /**
     * Base URL that <code>Document</code>or<code>FrameOwner</code> node uses for URL completion.
     */
    public void setBaseURL(Integer baseURL) {
        this.baseURL = baseURL;
    }

    /**
     * Contains the document's content language.
     */
    public Integer getContentLanguage() {
        return contentLanguage;
    }

    /**
     * Contains the document's content language.
     */
    public void setContentLanguage(Integer contentLanguage) {
        this.contentLanguage = contentLanguage;
    }

    /**
     * Contains the document's character set encoding.
     */
    public Integer getEncodingName() {
        return encodingName;
    }

    /**
     * Contains the document's character set encoding.
     */
    public void setEncodingName(Integer encodingName) {
        this.encodingName = encodingName;
    }

    /**
     * <code>DocumentType</code> node's publicId.
     */
    public Integer getPublicId() {
        return publicId;
    }

    /**
     * <code>DocumentType</code> node's publicId.
     */
    public void setPublicId(Integer publicId) {
        this.publicId = publicId;
    }

    /**
     * <code>DocumentType</code> node's systemId.
     */
    public Integer getSystemId() {
        return systemId;
    }

    /**
     * <code>DocumentType</code> node's systemId.
     */
    public void setSystemId(Integer systemId) {
        this.systemId = systemId;
    }

    /**
     * Frame ID for frame owner elements and also for the document node.
     */
    public Integer getFrameId() {
        return frameId;
    }

    /**
     * Frame ID for frame owner elements and also for the document node.
     */
    public void setFrameId(Integer frameId) {
        this.frameId = frameId;
    }

    /**
     * A table with dom nodes.
     */
    public NodeTreeSnapshot getNodes() {
        return nodes;
    }

    /**
     * A table with dom nodes.
     */
    public void setNodes(NodeTreeSnapshot nodes) {
        this.nodes = nodes;
    }

    /**
     * The nodes in the layout tree.
     */
    public LayoutTreeSnapshot getLayout() {
        return layout;
    }

    /**
     * The nodes in the layout tree.
     */
    public void setLayout(LayoutTreeSnapshot layout) {
        this.layout = layout;
    }

    /**
     * The post-layout inline text nodes.
     */
    public TextBoxSnapshot getTextBoxes() {
        return textBoxes;
    }

    /**
     * The post-layout inline text nodes.
     */
    public void setTextBoxes(TextBoxSnapshot textBoxes) {
        this.textBoxes = textBoxes;
    }
}
