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
package io.webfolder.cdp.type.network;

import io.webfolder.cdp.annotation.Experimental;

/**
 * Information about a signed exchange response
 */
@Experimental
public class SignedExchangeError {
    private String message;

    private Integer signatureIndex;

    private SignedExchangeErrorField errorField;

    /**
     * Error message.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Error message.
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * The index of the signature which caused the error.
     */
    public Integer getSignatureIndex() {
        return signatureIndex;
    }

    /**
     * The index of the signature which caused the error.
     */
    public void setSignatureIndex(Integer signatureIndex) {
        this.signatureIndex = signatureIndex;
    }

    /**
     * The field which caused the error.
     */
    public SignedExchangeErrorField getErrorField() {
        return errorField;
    }

    /**
     * The field which caused the error.
     */
    public void setErrorField(SignedExchangeErrorField errorField) {
        this.errorField = errorField;
    }
}
