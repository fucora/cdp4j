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
package io.webfolder.cdp.type.memory;

import java.util.ArrayList;
import java.util.List;

/**
 * Heap profile sample
 */
public class SamplingProfileNode {
    private Double size;

    private Double total;

    private List<String> stack = new ArrayList<>();

    /**
     * Size of the sampled allocation.
     */
    public Double getSize() {
        return size;
    }

    /**
     * Size of the sampled allocation.
     */
    public void setSize(Double size) {
        this.size = size;
    }

    /**
     * Total bytes attributed to this sample.
     */
    public Double getTotal() {
        return total;
    }

    /**
     * Total bytes attributed to this sample.
     */
    public void setTotal(Double total) {
        this.total = total;
    }

    /**
     * Execution stack at the point of allocation.
     */
    public List<String> getStack() {
        return stack;
    }

    /**
     * Execution stack at the point of allocation.
     */
    public void setStack(List<String> stack) {
        this.stack = stack;
    }
}
