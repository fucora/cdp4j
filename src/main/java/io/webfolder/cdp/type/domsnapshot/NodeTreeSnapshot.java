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

import java.util.ArrayList;
import java.util.List;

/**
 * Table containing nodes
 */
public class NodeTreeSnapshot {
    private List<Integer> parentIndex = new ArrayList<>();

    private List<Integer> nodeType = new ArrayList<>();

    private List<Integer> nodeName = new ArrayList<>();

    private List<Integer> nodeValue = new ArrayList<>();

    private List<Integer> backendNodeId = new ArrayList<>();

    private RareStringData textValue;

    private RareStringData inputValue;

    private RareBooleanData inputChecked;

    private RareBooleanData optionSelected;

    private RareIntegerData contentDocumentIndex;

    private RareStringData pseudoType;

    private RareBooleanData isClickable;

    private RareStringData currentSourceURL;

    private RareStringData originURL;

    /**
     * Parent node index.
     */
    public List<Integer> getParentIndex() {
        return parentIndex;
    }

    /**
     * Parent node index.
     */
    public void setParentIndex(List<Integer> parentIndex) {
        this.parentIndex = parentIndex;
    }

    /**
     * <code>Node</code>'s nodeType.
     */
    public List<Integer> getNodeType() {
        return nodeType;
    }

    /**
     * <code>Node</code>'s nodeType.
     */
    public void setNodeType(List<Integer> nodeType) {
        this.nodeType = nodeType;
    }

    /**
     * <code>Node</code>'s nodeName.
     */
    public List<Integer> getNodeName() {
        return nodeName;
    }

    /**
     * <code>Node</code>'s nodeName.
     */
    public void setNodeName(List<Integer> nodeName) {
        this.nodeName = nodeName;
    }

    /**
     * <code>Node</code>'s nodeValue.
     */
    public List<Integer> getNodeValue() {
        return nodeValue;
    }

    /**
     * <code>Node</code>'s nodeValue.
     */
    public void setNodeValue(List<Integer> nodeValue) {
        this.nodeValue = nodeValue;
    }

    /**
     * <code>Node</code>'s id, corresponds to DOM.Node.backendNodeId.
     */
    public List<Integer> getBackendNodeId() {
        return backendNodeId;
    }

    /**
     * <code>Node</code>'s id, corresponds to DOM.Node.backendNodeId.
     */
    public void setBackendNodeId(List<Integer> backendNodeId) {
        this.backendNodeId = backendNodeId;
    }

    /**
     * Only set for textarea elements, contains the text value.
     */
    public RareStringData getTextValue() {
        return textValue;
    }

    /**
     * Only set for textarea elements, contains the text value.
     */
    public void setTextValue(RareStringData textValue) {
        this.textValue = textValue;
    }

    /**
     * Only set for input elements, contains the input's associated text value.
     */
    public RareStringData getInputValue() {
        return inputValue;
    }

    /**
     * Only set for input elements, contains the input's associated text value.
     */
    public void setInputValue(RareStringData inputValue) {
        this.inputValue = inputValue;
    }

    /**
     * Only set for radio and checkbox input elements, indicates if the element has been checked
     */
    public RareBooleanData getInputChecked() {
        return inputChecked;
    }

    /**
     * Only set for radio and checkbox input elements, indicates if the element has been checked
     */
    public void setInputChecked(RareBooleanData inputChecked) {
        this.inputChecked = inputChecked;
    }

    /**
     * Only set for option elements, indicates if the element has been selected
     */
    public RareBooleanData getOptionSelected() {
        return optionSelected;
    }

    /**
     * Only set for option elements, indicates if the element has been selected
     */
    public void setOptionSelected(RareBooleanData optionSelected) {
        this.optionSelected = optionSelected;
    }

    /**
     * The index of the document in the list of the snapshot documents.
     */
    public RareIntegerData getContentDocumentIndex() {
        return contentDocumentIndex;
    }

    /**
     * The index of the document in the list of the snapshot documents.
     */
    public void setContentDocumentIndex(RareIntegerData contentDocumentIndex) {
        this.contentDocumentIndex = contentDocumentIndex;
    }

    /**
     * Type of a pseudo element node.
     */
    public RareStringData getPseudoType() {
        return pseudoType;
    }

    /**
     * Type of a pseudo element node.
     */
    public void setPseudoType(RareStringData pseudoType) {
        this.pseudoType = pseudoType;
    }

    /**
     * Whether this DOM node responds to mouse clicks. This includes nodes that have had click
     * event listeners attached via JavaScript as well as anchor tags that naturally navigate when
     * clicked.
     */
    public RareBooleanData getIsClickable() {
        return isClickable;
    }

    /**
     * Whether this DOM node responds to mouse clicks. This includes nodes that have had click
     * event listeners attached via JavaScript as well as anchor tags that naturally navigate when
     * clicked.
     */
    public void setIsClickable(RareBooleanData isClickable) {
        this.isClickable = isClickable;
    }

    /**
     * The selected url for nodes with a srcset attribute.
     */
    public RareStringData getCurrentSourceURL() {
        return currentSourceURL;
    }

    /**
     * The selected url for nodes with a srcset attribute.
     */
    public void setCurrentSourceURL(RareStringData currentSourceURL) {
        this.currentSourceURL = currentSourceURL;
    }

    /**
     * The url of the script (if any) that generates this node.
     */
    public RareStringData getOriginURL() {
        return originURL;
    }

    /**
     * The url of the script (if any) that generates this node.
     */
    public void setOriginURL(RareStringData originURL) {
        this.originURL = originURL;
    }
}
