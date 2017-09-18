/**
 * cpd4j - Chrome DevTools Protocol for Java
 * Copyright © 2017 WebFolder OÜ (support@webfolder.io)
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
package io.webfolder.cdp.session;

import static io.webfolder.cdp.event.Events.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import io.webfolder.cdp.event.Events;
import io.webfolder.cdp.listener.*;

public class Listener {

    private Map<Events, List<AbstractListener<?>>> listeners = new HashMap<>();

    public Listener(final Session session) {
        session.addEventListener(new RootListener(this));
    }

    private static class RootListener implements EventListener<Object> {

        private Listener that;

        public RootListener(Listener that) {
            this.that = that;
        }

        @Override
        @SuppressWarnings({ "unchecked", "rawtypes" })
        public void onEvent(Events event, Object value) {
            List<AbstractListener<?>> map = that.listeners.get(event);
            if (map != null && !map.isEmpty()) {
                Iterator<AbstractListener<?>> iter = map.iterator();
                while (iter.hasNext()) {
                    AbstractListener listener = iter.next();
                    listener.onEvent(value);
                }
            }
        }
    }

    public boolean addInspectorDetachedListener(InspectorDetachedListener listener) {
        synchronized (this) {
            List<AbstractListener<?>> list = listeners.get(InspectorDetached);
            if (list == null) {
                list = new ArrayList<>(1);
                listeners.put(InspectorDetached, list);
            }
            return list.add(listener);
        }
    }

    public boolean addInspectorTargetCrashedListener(InspectorTargetCrashedListener listener) {
        synchronized (this) {
            List<AbstractListener<?>> list = listeners.get(InspectorTargetCrashed);
            if (list == null) {
                list = new ArrayList<>(1);
                listeners.put(InspectorTargetCrashed, list);
            }
            return list.add(listener);
        }
    }

    public boolean addPerformanceMetricsListener(PerformanceMetricsListener listener) {
        synchronized (this) {
            List<AbstractListener<?>> list = listeners.get(PerformanceMetrics);
            if (list == null) {
                list = new ArrayList<>(1);
                listeners.put(PerformanceMetrics, list);
            }
            return list.add(listener);
        }
    }

    public boolean addPageDomContentEventFiredListener(PageDomContentEventFiredListener listener) {
        synchronized (this) {
            List<AbstractListener<?>> list = listeners.get(PageDomContentEventFired);
            if (list == null) {
                list = new ArrayList<>(1);
                listeners.put(PageDomContentEventFired, list);
            }
            return list.add(listener);
        }
    }

    public boolean addPageLoadEventFiredListener(PageLoadEventFiredListener listener) {
        synchronized (this) {
            List<AbstractListener<?>> list = listeners.get(PageLoadEventFired);
            if (list == null) {
                list = new ArrayList<>(1);
                listeners.put(PageLoadEventFired, list);
            }
            return list.add(listener);
        }
    }

    public boolean addPageLifecycleEventListener(PageLifecycleEventListener listener) {
        synchronized (this) {
            List<AbstractListener<?>> list = listeners.get(PageLifecycleEvent);
            if (list == null) {
                list = new ArrayList<>(1);
                listeners.put(PageLifecycleEvent, list);
            }
            return list.add(listener);
        }
    }

    public boolean addPageFrameAttachedListener(PageFrameAttachedListener listener) {
        synchronized (this) {
            List<AbstractListener<?>> list = listeners.get(PageFrameAttached);
            if (list == null) {
                list = new ArrayList<>(1);
                listeners.put(PageFrameAttached, list);
            }
            return list.add(listener);
        }
    }

    public boolean addPageFrameNavigatedListener(PageFrameNavigatedListener listener) {
        synchronized (this) {
            List<AbstractListener<?>> list = listeners.get(PageFrameNavigated);
            if (list == null) {
                list = new ArrayList<>(1);
                listeners.put(PageFrameNavigated, list);
            }
            return list.add(listener);
        }
    }

    public boolean addPageFrameDetachedListener(PageFrameDetachedListener listener) {
        synchronized (this) {
            List<AbstractListener<?>> list = listeners.get(PageFrameDetached);
            if (list == null) {
                list = new ArrayList<>(1);
                listeners.put(PageFrameDetached, list);
            }
            return list.add(listener);
        }
    }

    public boolean addPageFrameStartedLoadingListener(PageFrameStartedLoadingListener listener) {
        synchronized (this) {
            List<AbstractListener<?>> list = listeners.get(PageFrameStartedLoading);
            if (list == null) {
                list = new ArrayList<>(1);
                listeners.put(PageFrameStartedLoading, list);
            }
            return list.add(listener);
        }
    }

    public boolean addPageFrameStoppedLoadingListener(PageFrameStoppedLoadingListener listener) {
        synchronized (this) {
            List<AbstractListener<?>> list = listeners.get(PageFrameStoppedLoading);
            if (list == null) {
                list = new ArrayList<>(1);
                listeners.put(PageFrameStoppedLoading, list);
            }
            return list.add(listener);
        }
    }

    public boolean addPageFrameScheduledNavigationListener(PageFrameScheduledNavigationListener listener) {
        synchronized (this) {
            List<AbstractListener<?>> list = listeners.get(PageFrameScheduledNavigation);
            if (list == null) {
                list = new ArrayList<>(1);
                listeners.put(PageFrameScheduledNavigation, list);
            }
            return list.add(listener);
        }
    }

    public boolean addPageFrameClearedScheduledNavigationListener(
            PageFrameClearedScheduledNavigationListener listener) {
        synchronized (this) {
            List<AbstractListener<?>> list = listeners.get(PageFrameClearedScheduledNavigation);
            if (list == null) {
                list = new ArrayList<>(1);
                listeners.put(PageFrameClearedScheduledNavigation, list);
            }
            return list.add(listener);
        }
    }

    public boolean addPageFrameResizedListener(PageFrameResizedListener listener) {
        synchronized (this) {
            List<AbstractListener<?>> list = listeners.get(PageFrameResized);
            if (list == null) {
                list = new ArrayList<>(1);
                listeners.put(PageFrameResized, list);
            }
            return list.add(listener);
        }
    }

    public boolean addPageJavascriptDialogOpeningListener(PageJavascriptDialogOpeningListener listener) {
        synchronized (this) {
            List<AbstractListener<?>> list = listeners.get(PageJavascriptDialogOpening);
            if (list == null) {
                list = new ArrayList<>(1);
                listeners.put(PageJavascriptDialogOpening, list);
            }
            return list.add(listener);
        }
    }

    public boolean addPageJavascriptDialogClosedListener(PageJavascriptDialogClosedListener listener) {
        synchronized (this) {
            List<AbstractListener<?>> list = listeners.get(PageJavascriptDialogClosed);
            if (list == null) {
                list = new ArrayList<>(1);
                listeners.put(PageJavascriptDialogClosed, list);
            }
            return list.add(listener);
        }
    }

    public boolean addPageScreencastFrameListener(PageScreencastFrameListener listener) {
        synchronized (this) {
            List<AbstractListener<?>> list = listeners.get(PageScreencastFrame);
            if (list == null) {
                list = new ArrayList<>(1);
                listeners.put(PageScreencastFrame, list);
            }
            return list.add(listener);
        }
    }

    public boolean addPageScreencastVisibilityChangedListener(PageScreencastVisibilityChangedListener listener) {
        synchronized (this) {
            List<AbstractListener<?>> list = listeners.get(PageScreencastVisibilityChanged);
            if (list == null) {
                list = new ArrayList<>(1);
                listeners.put(PageScreencastVisibilityChanged, list);
            }
            return list.add(listener);
        }
    }

    public boolean addPageInterstitialShownListener(PageInterstitialShownListener listener) {
        synchronized (this) {
            List<AbstractListener<?>> list = listeners.get(PageInterstitialShown);
            if (list == null) {
                list = new ArrayList<>(1);
                listeners.put(PageInterstitialShown, list);
            }
            return list.add(listener);
        }
    }

    public boolean addPageInterstitialHiddenListener(PageInterstitialHiddenListener listener) {
        synchronized (this) {
            List<AbstractListener<?>> list = listeners.get(PageInterstitialHidden);
            if (list == null) {
                list = new ArrayList<>(1);
                listeners.put(PageInterstitialHidden, list);
            }
            return list.add(listener);
        }
    }

    public boolean addOverlayNodeHighlightRequestedListener(OverlayNodeHighlightRequestedListener listener) {
        synchronized (this) {
            List<AbstractListener<?>> list = listeners.get(OverlayNodeHighlightRequested);
            if (list == null) {
                list = new ArrayList<>(1);
                listeners.put(OverlayNodeHighlightRequested, list);
            }
            return list.add(listener);
        }
    }

    public boolean addOverlayInspectNodeRequestedListener(OverlayInspectNodeRequestedListener listener) {
        synchronized (this) {
            List<AbstractListener<?>> list = listeners.get(OverlayInspectNodeRequested);
            if (list == null) {
                list = new ArrayList<>(1);
                listeners.put(OverlayInspectNodeRequested, list);
            }
            return list.add(listener);
        }
    }

    public boolean addOverlayScreenshotRequestedListener(OverlayScreenshotRequestedListener listener) {
        synchronized (this) {
            List<AbstractListener<?>> list = listeners.get(OverlayScreenshotRequested);
            if (list == null) {
                list = new ArrayList<>(1);
                listeners.put(OverlayScreenshotRequested, list);
            }
            return list.add(listener);
        }
    }

    public boolean addEmulationVirtualTimeBudgetExpiredListener(EmulationVirtualTimeBudgetExpiredListener listener) {
        synchronized (this) {
            List<AbstractListener<?>> list = listeners.get(EmulationVirtualTimeBudgetExpired);
            if (list == null) {
                list = new ArrayList<>(1);
                listeners.put(EmulationVirtualTimeBudgetExpired, list);
            }
            return list.add(listener);
        }
    }

    public boolean addEmulationVirtualTimePausedListener(EmulationVirtualTimePausedListener listener) {
        synchronized (this) {
            List<AbstractListener<?>> list = listeners.get(EmulationVirtualTimePaused);
            if (list == null) {
                list = new ArrayList<>(1);
                listeners.put(EmulationVirtualTimePaused, list);
            }
            return list.add(listener);
        }
    }

    public boolean addSecuritySecurityStateChangedListener(SecuritySecurityStateChangedListener listener) {
        synchronized (this) {
            List<AbstractListener<?>> list = listeners.get(SecuritySecurityStateChanged);
            if (list == null) {
                list = new ArrayList<>(1);
                listeners.put(SecuritySecurityStateChanged, list);
            }
            return list.add(listener);
        }
    }

    public boolean addSecurityCertificateErrorListener(SecurityCertificateErrorListener listener) {
        synchronized (this) {
            List<AbstractListener<?>> list = listeners.get(SecurityCertificateError);
            if (list == null) {
                list = new ArrayList<>(1);
                listeners.put(SecurityCertificateError, list);
            }
            return list.add(listener);
        }
    }

    public boolean addNetworkResourceChangedPriorityListener(NetworkResourceChangedPriorityListener listener) {
        synchronized (this) {
            List<AbstractListener<?>> list = listeners.get(NetworkResourceChangedPriority);
            if (list == null) {
                list = new ArrayList<>(1);
                listeners.put(NetworkResourceChangedPriority, list);
            }
            return list.add(listener);
        }
    }

    public boolean addNetworkRequestWillBeSentListener(NetworkRequestWillBeSentListener listener) {
        synchronized (this) {
            List<AbstractListener<?>> list = listeners.get(NetworkRequestWillBeSent);
            if (list == null) {
                list = new ArrayList<>(1);
                listeners.put(NetworkRequestWillBeSent, list);
            }
            return list.add(listener);
        }
    }

    public boolean addNetworkRequestServedFromCacheListener(NetworkRequestServedFromCacheListener listener) {
        synchronized (this) {
            List<AbstractListener<?>> list = listeners.get(NetworkRequestServedFromCache);
            if (list == null) {
                list = new ArrayList<>(1);
                listeners.put(NetworkRequestServedFromCache, list);
            }
            return list.add(listener);
        }
    }

    public boolean addNetworkResponseReceivedListener(NetworkResponseReceivedListener listener) {
        synchronized (this) {
            List<AbstractListener<?>> list = listeners.get(NetworkResponseReceived);
            if (list == null) {
                list = new ArrayList<>(1);
                listeners.put(NetworkResponseReceived, list);
            }
            return list.add(listener);
        }
    }

    public boolean addNetworkDataReceivedListener(NetworkDataReceivedListener listener) {
        synchronized (this) {
            List<AbstractListener<?>> list = listeners.get(NetworkDataReceived);
            if (list == null) {
                list = new ArrayList<>(1);
                listeners.put(NetworkDataReceived, list);
            }
            return list.add(listener);
        }
    }

    public boolean addNetworkLoadingFinishedListener(NetworkLoadingFinishedListener listener) {
        synchronized (this) {
            List<AbstractListener<?>> list = listeners.get(NetworkLoadingFinished);
            if (list == null) {
                list = new ArrayList<>(1);
                listeners.put(NetworkLoadingFinished, list);
            }
            return list.add(listener);
        }
    }

    public boolean addNetworkLoadingFailedListener(NetworkLoadingFailedListener listener) {
        synchronized (this) {
            List<AbstractListener<?>> list = listeners.get(NetworkLoadingFailed);
            if (list == null) {
                list = new ArrayList<>(1);
                listeners.put(NetworkLoadingFailed, list);
            }
            return list.add(listener);
        }
    }

    public boolean addNetworkWebSocketWillSendHandshakeRequestListener(
            NetworkWebSocketWillSendHandshakeRequestListener listener) {
        synchronized (this) {
            List<AbstractListener<?>> list = listeners.get(NetworkWebSocketWillSendHandshakeRequest);
            if (list == null) {
                list = new ArrayList<>(1);
                listeners.put(NetworkWebSocketWillSendHandshakeRequest, list);
            }
            return list.add(listener);
        }
    }

    public boolean addNetworkWebSocketHandshakeResponseReceivedListener(
            NetworkWebSocketHandshakeResponseReceivedListener listener) {
        synchronized (this) {
            List<AbstractListener<?>> list = listeners.get(NetworkWebSocketHandshakeResponseReceived);
            if (list == null) {
                list = new ArrayList<>(1);
                listeners.put(NetworkWebSocketHandshakeResponseReceived, list);
            }
            return list.add(listener);
        }
    }

    public boolean addNetworkWebSocketCreatedListener(NetworkWebSocketCreatedListener listener) {
        synchronized (this) {
            List<AbstractListener<?>> list = listeners.get(NetworkWebSocketCreated);
            if (list == null) {
                list = new ArrayList<>(1);
                listeners.put(NetworkWebSocketCreated, list);
            }
            return list.add(listener);
        }
    }

    public boolean addNetworkWebSocketClosedListener(NetworkWebSocketClosedListener listener) {
        synchronized (this) {
            List<AbstractListener<?>> list = listeners.get(NetworkWebSocketClosed);
            if (list == null) {
                list = new ArrayList<>(1);
                listeners.put(NetworkWebSocketClosed, list);
            }
            return list.add(listener);
        }
    }

    public boolean addNetworkWebSocketFrameReceivedListener(NetworkWebSocketFrameReceivedListener listener) {
        synchronized (this) {
            List<AbstractListener<?>> list = listeners.get(NetworkWebSocketFrameReceived);
            if (list == null) {
                list = new ArrayList<>(1);
                listeners.put(NetworkWebSocketFrameReceived, list);
            }
            return list.add(listener);
        }
    }

    public boolean addNetworkWebSocketFrameErrorListener(NetworkWebSocketFrameErrorListener listener) {
        synchronized (this) {
            List<AbstractListener<?>> list = listeners.get(NetworkWebSocketFrameError);
            if (list == null) {
                list = new ArrayList<>(1);
                listeners.put(NetworkWebSocketFrameError, list);
            }
            return list.add(listener);
        }
    }

    public boolean addNetworkWebSocketFrameSentListener(NetworkWebSocketFrameSentListener listener) {
        synchronized (this) {
            List<AbstractListener<?>> list = listeners.get(NetworkWebSocketFrameSent);
            if (list == null) {
                list = new ArrayList<>(1);
                listeners.put(NetworkWebSocketFrameSent, list);
            }
            return list.add(listener);
        }
    }

    public boolean addNetworkEventSourceMessageReceivedListener(NetworkEventSourceMessageReceivedListener listener) {
        synchronized (this) {
            List<AbstractListener<?>> list = listeners.get(NetworkEventSourceMessageReceived);
            if (list == null) {
                list = new ArrayList<>(1);
                listeners.put(NetworkEventSourceMessageReceived, list);
            }
            return list.add(listener);
        }
    }

    public boolean addNetworkRequestInterceptedListener(NetworkRequestInterceptedListener listener) {
        synchronized (this) {
            List<AbstractListener<?>> list = listeners.get(NetworkRequestIntercepted);
            if (list == null) {
                list = new ArrayList<>(1);
                listeners.put(NetworkRequestIntercepted, list);
            }
            return list.add(listener);
        }
    }

    public boolean addDatabaseAddDatabaseListener(DatabaseAddDatabaseListener listener) {
        synchronized (this) {
            List<AbstractListener<?>> list = listeners.get(DatabaseAddDatabase);
            if (list == null) {
                list = new ArrayList<>(1);
                listeners.put(DatabaseAddDatabase, list);
            }
            return list.add(listener);
        }
    }

    public boolean addDOMStorageDomStorageItemsClearedListener(DOMStorageDomStorageItemsClearedListener listener) {
        synchronized (this) {
            List<AbstractListener<?>> list = listeners.get(DOMStorageDomStorageItemsCleared);
            if (list == null) {
                list = new ArrayList<>(1);
                listeners.put(DOMStorageDomStorageItemsCleared, list);
            }
            return list.add(listener);
        }
    }

    public boolean addDOMStorageDomStorageItemRemovedListener(DOMStorageDomStorageItemRemovedListener listener) {
        synchronized (this) {
            List<AbstractListener<?>> list = listeners.get(DOMStorageDomStorageItemRemoved);
            if (list == null) {
                list = new ArrayList<>(1);
                listeners.put(DOMStorageDomStorageItemRemoved, list);
            }
            return list.add(listener);
        }
    }

    public boolean addDOMStorageDomStorageItemAddedListener(DOMStorageDomStorageItemAddedListener listener) {
        synchronized (this) {
            List<AbstractListener<?>> list = listeners.get(DOMStorageDomStorageItemAdded);
            if (list == null) {
                list = new ArrayList<>(1);
                listeners.put(DOMStorageDomStorageItemAdded, list);
            }
            return list.add(listener);
        }
    }

    public boolean addDOMStorageDomStorageItemUpdatedListener(DOMStorageDomStorageItemUpdatedListener listener) {
        synchronized (this) {
            List<AbstractListener<?>> list = listeners.get(DOMStorageDomStorageItemUpdated);
            if (list == null) {
                list = new ArrayList<>(1);
                listeners.put(DOMStorageDomStorageItemUpdated, list);
            }
            return list.add(listener);
        }
    }

    public boolean addApplicationCacheApplicationCacheStatusUpdatedListener(
            ApplicationCacheApplicationCacheStatusUpdatedListener listener) {
        synchronized (this) {
            List<AbstractListener<?>> list = listeners.get(ApplicationCacheApplicationCacheStatusUpdated);
            if (list == null) {
                list = new ArrayList<>(1);
                listeners.put(ApplicationCacheApplicationCacheStatusUpdated, list);
            }
            return list.add(listener);
        }
    }

    public boolean addApplicationCacheNetworkStateUpdatedListener(
            ApplicationCacheNetworkStateUpdatedListener listener) {
        synchronized (this) {
            List<AbstractListener<?>> list = listeners.get(ApplicationCacheNetworkStateUpdated);
            if (list == null) {
                list = new ArrayList<>(1);
                listeners.put(ApplicationCacheNetworkStateUpdated, list);
            }
            return list.add(listener);
        }
    }

    public boolean addDOMDocumentUpdatedListener(DOMDocumentUpdatedListener listener) {
        synchronized (this) {
            List<AbstractListener<?>> list = listeners.get(DOMDocumentUpdated);
            if (list == null) {
                list = new ArrayList<>(1);
                listeners.put(DOMDocumentUpdated, list);
            }
            return list.add(listener);
        }
    }

    public boolean addDOMSetChildNodesListener(DOMSetChildNodesListener listener) {
        synchronized (this) {
            List<AbstractListener<?>> list = listeners.get(DOMSetChildNodes);
            if (list == null) {
                list = new ArrayList<>(1);
                listeners.put(DOMSetChildNodes, list);
            }
            return list.add(listener);
        }
    }

    public boolean addDOMAttributeModifiedListener(DOMAttributeModifiedListener listener) {
        synchronized (this) {
            List<AbstractListener<?>> list = listeners.get(DOMAttributeModified);
            if (list == null) {
                list = new ArrayList<>(1);
                listeners.put(DOMAttributeModified, list);
            }
            return list.add(listener);
        }
    }

    public boolean addDOMAttributeRemovedListener(DOMAttributeRemovedListener listener) {
        synchronized (this) {
            List<AbstractListener<?>> list = listeners.get(DOMAttributeRemoved);
            if (list == null) {
                list = new ArrayList<>(1);
                listeners.put(DOMAttributeRemoved, list);
            }
            return list.add(listener);
        }
    }

    public boolean addDOMInlineStyleInvalidatedListener(DOMInlineStyleInvalidatedListener listener) {
        synchronized (this) {
            List<AbstractListener<?>> list = listeners.get(DOMInlineStyleInvalidated);
            if (list == null) {
                list = new ArrayList<>(1);
                listeners.put(DOMInlineStyleInvalidated, list);
            }
            return list.add(listener);
        }
    }

    public boolean addDOMCharacterDataModifiedListener(DOMCharacterDataModifiedListener listener) {
        synchronized (this) {
            List<AbstractListener<?>> list = listeners.get(DOMCharacterDataModified);
            if (list == null) {
                list = new ArrayList<>(1);
                listeners.put(DOMCharacterDataModified, list);
            }
            return list.add(listener);
        }
    }

    public boolean addDOMChildNodeCountUpdatedListener(DOMChildNodeCountUpdatedListener listener) {
        synchronized (this) {
            List<AbstractListener<?>> list = listeners.get(DOMChildNodeCountUpdated);
            if (list == null) {
                list = new ArrayList<>(1);
                listeners.put(DOMChildNodeCountUpdated, list);
            }
            return list.add(listener);
        }
    }

    public boolean addDOMChildNodeInsertedListener(DOMChildNodeInsertedListener listener) {
        synchronized (this) {
            List<AbstractListener<?>> list = listeners.get(DOMChildNodeInserted);
            if (list == null) {
                list = new ArrayList<>(1);
                listeners.put(DOMChildNodeInserted, list);
            }
            return list.add(listener);
        }
    }

    public boolean addDOMChildNodeRemovedListener(DOMChildNodeRemovedListener listener) {
        synchronized (this) {
            List<AbstractListener<?>> list = listeners.get(DOMChildNodeRemoved);
            if (list == null) {
                list = new ArrayList<>(1);
                listeners.put(DOMChildNodeRemoved, list);
            }
            return list.add(listener);
        }
    }

    public boolean addDOMShadowRootPushedListener(DOMShadowRootPushedListener listener) {
        synchronized (this) {
            List<AbstractListener<?>> list = listeners.get(DOMShadowRootPushed);
            if (list == null) {
                list = new ArrayList<>(1);
                listeners.put(DOMShadowRootPushed, list);
            }
            return list.add(listener);
        }
    }

    public boolean addDOMShadowRootPoppedListener(DOMShadowRootPoppedListener listener) {
        synchronized (this) {
            List<AbstractListener<?>> list = listeners.get(DOMShadowRootPopped);
            if (list == null) {
                list = new ArrayList<>(1);
                listeners.put(DOMShadowRootPopped, list);
            }
            return list.add(listener);
        }
    }

    public boolean addDOMPseudoElementAddedListener(DOMPseudoElementAddedListener listener) {
        synchronized (this) {
            List<AbstractListener<?>> list = listeners.get(DOMPseudoElementAdded);
            if (list == null) {
                list = new ArrayList<>(1);
                listeners.put(DOMPseudoElementAdded, list);
            }
            return list.add(listener);
        }
    }

    public boolean addDOMPseudoElementRemovedListener(DOMPseudoElementRemovedListener listener) {
        synchronized (this) {
            List<AbstractListener<?>> list = listeners.get(DOMPseudoElementRemoved);
            if (list == null) {
                list = new ArrayList<>(1);
                listeners.put(DOMPseudoElementRemoved, list);
            }
            return list.add(listener);
        }
    }

    public boolean addDOMDistributedNodesUpdatedListener(DOMDistributedNodesUpdatedListener listener) {
        synchronized (this) {
            List<AbstractListener<?>> list = listeners.get(DOMDistributedNodesUpdated);
            if (list == null) {
                list = new ArrayList<>(1);
                listeners.put(DOMDistributedNodesUpdated, list);
            }
            return list.add(listener);
        }
    }

    public boolean addCSSMediaQueryResultChangedListener(CSSMediaQueryResultChangedListener listener) {
        synchronized (this) {
            List<AbstractListener<?>> list = listeners.get(CSSMediaQueryResultChanged);
            if (list == null) {
                list = new ArrayList<>(1);
                listeners.put(CSSMediaQueryResultChanged, list);
            }
            return list.add(listener);
        }
    }

    public boolean addCSSFontsUpdatedListener(CSSFontsUpdatedListener listener) {
        synchronized (this) {
            List<AbstractListener<?>> list = listeners.get(CSSFontsUpdated);
            if (list == null) {
                list = new ArrayList<>(1);
                listeners.put(CSSFontsUpdated, list);
            }
            return list.add(listener);
        }
    }

    public boolean addCSSStyleSheetChangedListener(CSSStyleSheetChangedListener listener) {
        synchronized (this) {
            List<AbstractListener<?>> list = listeners.get(CSSStyleSheetChanged);
            if (list == null) {
                list = new ArrayList<>(1);
                listeners.put(CSSStyleSheetChanged, list);
            }
            return list.add(listener);
        }
    }

    public boolean addCSSStyleSheetAddedListener(CSSStyleSheetAddedListener listener) {
        synchronized (this) {
            List<AbstractListener<?>> list = listeners.get(CSSStyleSheetAdded);
            if (list == null) {
                list = new ArrayList<>(1);
                listeners.put(CSSStyleSheetAdded, list);
            }
            return list.add(listener);
        }
    }

    public boolean addCSSStyleSheetRemovedListener(CSSStyleSheetRemovedListener listener) {
        synchronized (this) {
            List<AbstractListener<?>> list = listeners.get(CSSStyleSheetRemoved);
            if (list == null) {
                list = new ArrayList<>(1);
                listeners.put(CSSStyleSheetRemoved, list);
            }
            return list.add(listener);
        }
    }

    public boolean addTargetTargetCreatedListener(TargetTargetCreatedListener listener) {
        synchronized (this) {
            List<AbstractListener<?>> list = listeners.get(TargetTargetCreated);
            if (list == null) {
                list = new ArrayList<>(1);
                listeners.put(TargetTargetCreated, list);
            }
            return list.add(listener);
        }
    }

    public boolean addTargetTargetInfoChangedListener(TargetTargetInfoChangedListener listener) {
        synchronized (this) {
            List<AbstractListener<?>> list = listeners.get(TargetTargetInfoChanged);
            if (list == null) {
                list = new ArrayList<>(1);
                listeners.put(TargetTargetInfoChanged, list);
            }
            return list.add(listener);
        }
    }

    public boolean addTargetTargetDestroyedListener(TargetTargetDestroyedListener listener) {
        synchronized (this) {
            List<AbstractListener<?>> list = listeners.get(TargetTargetDestroyed);
            if (list == null) {
                list = new ArrayList<>(1);
                listeners.put(TargetTargetDestroyed, list);
            }
            return list.add(listener);
        }
    }

    public boolean addTargetAttachedToTargetListener(TargetAttachedToTargetListener listener) {
        synchronized (this) {
            List<AbstractListener<?>> list = listeners.get(TargetAttachedToTarget);
            if (list == null) {
                list = new ArrayList<>(1);
                listeners.put(TargetAttachedToTarget, list);
            }
            return list.add(listener);
        }
    }

    public boolean addTargetDetachedFromTargetListener(TargetDetachedFromTargetListener listener) {
        synchronized (this) {
            List<AbstractListener<?>> list = listeners.get(TargetDetachedFromTarget);
            if (list == null) {
                list = new ArrayList<>(1);
                listeners.put(TargetDetachedFromTarget, list);
            }
            return list.add(listener);
        }
    }

    public boolean addTargetReceivedMessageFromTargetListener(TargetReceivedMessageFromTargetListener listener) {
        synchronized (this) {
            List<AbstractListener<?>> list = listeners.get(TargetReceivedMessageFromTarget);
            if (list == null) {
                list = new ArrayList<>(1);
                listeners.put(TargetReceivedMessageFromTarget, list);
            }
            return list.add(listener);
        }
    }

    public boolean addServiceWorkerWorkerRegistrationUpdatedListener(
            ServiceWorkerWorkerRegistrationUpdatedListener listener) {
        synchronized (this) {
            List<AbstractListener<?>> list = listeners.get(ServiceWorkerWorkerRegistrationUpdated);
            if (list == null) {
                list = new ArrayList<>(1);
                listeners.put(ServiceWorkerWorkerRegistrationUpdated, list);
            }
            return list.add(listener);
        }
    }

    public boolean addServiceWorkerWorkerVersionUpdatedListener(ServiceWorkerWorkerVersionUpdatedListener listener) {
        synchronized (this) {
            List<AbstractListener<?>> list = listeners.get(ServiceWorkerWorkerVersionUpdated);
            if (list == null) {
                list = new ArrayList<>(1);
                listeners.put(ServiceWorkerWorkerVersionUpdated, list);
            }
            return list.add(listener);
        }
    }

    public boolean addServiceWorkerWorkerErrorReportedListener(ServiceWorkerWorkerErrorReportedListener listener) {
        synchronized (this) {
            List<AbstractListener<?>> list = listeners.get(ServiceWorkerWorkerErrorReported);
            if (list == null) {
                list = new ArrayList<>(1);
                listeners.put(ServiceWorkerWorkerErrorReported, list);
            }
            return list.add(listener);
        }
    }

    public boolean addLayerTreeLayerTreeDidChangeListener(LayerTreeLayerTreeDidChangeListener listener) {
        synchronized (this) {
            List<AbstractListener<?>> list = listeners.get(LayerTreeLayerTreeDidChange);
            if (list == null) {
                list = new ArrayList<>(1);
                listeners.put(LayerTreeLayerTreeDidChange, list);
            }
            return list.add(listener);
        }
    }

    public boolean addLayerTreeLayerPaintedListener(LayerTreeLayerPaintedListener listener) {
        synchronized (this) {
            List<AbstractListener<?>> list = listeners.get(LayerTreeLayerPainted);
            if (list == null) {
                list = new ArrayList<>(1);
                listeners.put(LayerTreeLayerPainted, list);
            }
            return list.add(listener);
        }
    }

    public boolean addTracingDataCollectedListener(TracingDataCollectedListener listener) {
        synchronized (this) {
            List<AbstractListener<?>> list = listeners.get(TracingDataCollected);
            if (list == null) {
                list = new ArrayList<>(1);
                listeners.put(TracingDataCollected, list);
            }
            return list.add(listener);
        }
    }

    public boolean addTracingTracingCompleteListener(TracingTracingCompleteListener listener) {
        synchronized (this) {
            List<AbstractListener<?>> list = listeners.get(TracingTracingComplete);
            if (list == null) {
                list = new ArrayList<>(1);
                listeners.put(TracingTracingComplete, list);
            }
            return list.add(listener);
        }
    }

    public boolean addTracingBufferUsageListener(TracingBufferUsageListener listener) {
        synchronized (this) {
            List<AbstractListener<?>> list = listeners.get(TracingBufferUsage);
            if (list == null) {
                list = new ArrayList<>(1);
                listeners.put(TracingBufferUsage, list);
            }
            return list.add(listener);
        }
    }

    public boolean addAnimationAnimationCreatedListener(AnimationAnimationCreatedListener listener) {
        synchronized (this) {
            List<AbstractListener<?>> list = listeners.get(AnimationAnimationCreated);
            if (list == null) {
                list = new ArrayList<>(1);
                listeners.put(AnimationAnimationCreated, list);
            }
            return list.add(listener);
        }
    }

    public boolean addAnimationAnimationStartedListener(AnimationAnimationStartedListener listener) {
        synchronized (this) {
            List<AbstractListener<?>> list = listeners.get(AnimationAnimationStarted);
            if (list == null) {
                list = new ArrayList<>(1);
                listeners.put(AnimationAnimationStarted, list);
            }
            return list.add(listener);
        }
    }

    public boolean addAnimationAnimationCanceledListener(AnimationAnimationCanceledListener listener) {
        synchronized (this) {
            List<AbstractListener<?>> list = listeners.get(AnimationAnimationCanceled);
            if (list == null) {
                list = new ArrayList<>(1);
                listeners.put(AnimationAnimationCanceled, list);
            }
            return list.add(listener);
        }
    }

    public boolean addStorageCacheStorageListUpdatedListener(StorageCacheStorageListUpdatedListener listener) {
        synchronized (this) {
            List<AbstractListener<?>> list = listeners.get(StorageCacheStorageListUpdated);
            if (list == null) {
                list = new ArrayList<>(1);
                listeners.put(StorageCacheStorageListUpdated, list);
            }
            return list.add(listener);
        }
    }

    public boolean addStorageCacheStorageContentUpdatedListener(StorageCacheStorageContentUpdatedListener listener) {
        synchronized (this) {
            List<AbstractListener<?>> list = listeners.get(StorageCacheStorageContentUpdated);
            if (list == null) {
                list = new ArrayList<>(1);
                listeners.put(StorageCacheStorageContentUpdated, list);
            }
            return list.add(listener);
        }
    }

    public boolean addLogEntryAddedListener(LogEntryAddedListener listener) {
        synchronized (this) {
            List<AbstractListener<?>> list = listeners.get(LogEntryAdded);
            if (list == null) {
                list = new ArrayList<>(1);
                listeners.put(LogEntryAdded, list);
            }
            return list.add(listener);
        }
    }

    public boolean addTetheringAcceptedListener(TetheringAcceptedListener listener) {
        synchronized (this) {
            List<AbstractListener<?>> list = listeners.get(TetheringAccepted);
            if (list == null) {
                list = new ArrayList<>(1);
                listeners.put(TetheringAccepted, list);
            }
            return list.add(listener);
        }
    }

    public boolean addRuntimeExecutionContextCreatedListener(RuntimeExecutionContextCreatedListener listener) {
        synchronized (this) {
            List<AbstractListener<?>> list = listeners.get(RuntimeExecutionContextCreated);
            if (list == null) {
                list = new ArrayList<>(1);
                listeners.put(RuntimeExecutionContextCreated, list);
            }
            return list.add(listener);
        }
    }

    public boolean addRuntimeExecutionContextDestroyedListener(RuntimeExecutionContextDestroyedListener listener) {
        synchronized (this) {
            List<AbstractListener<?>> list = listeners.get(RuntimeExecutionContextDestroyed);
            if (list == null) {
                list = new ArrayList<>(1);
                listeners.put(RuntimeExecutionContextDestroyed, list);
            }
            return list.add(listener);
        }
    }

    public boolean addRuntimeExecutionContextsClearedListener(RuntimeExecutionContextsClearedListener listener) {
        synchronized (this) {
            List<AbstractListener<?>> list = listeners.get(RuntimeExecutionContextsCleared);
            if (list == null) {
                list = new ArrayList<>(1);
                listeners.put(RuntimeExecutionContextsCleared, list);
            }
            return list.add(listener);
        }
    }

    public boolean addRuntimeExceptionThrownListener(RuntimeExceptionThrownListener listener) {
        synchronized (this) {
            List<AbstractListener<?>> list = listeners.get(RuntimeExceptionThrown);
            if (list == null) {
                list = new ArrayList<>(1);
                listeners.put(RuntimeExceptionThrown, list);
            }
            return list.add(listener);
        }
    }

    public boolean addRuntimeExceptionRevokedListener(RuntimeExceptionRevokedListener listener) {
        synchronized (this) {
            List<AbstractListener<?>> list = listeners.get(RuntimeExceptionRevoked);
            if (list == null) {
                list = new ArrayList<>(1);
                listeners.put(RuntimeExceptionRevoked, list);
            }
            return list.add(listener);
        }
    }

    public boolean addRuntimeConsoleAPICalledListener(RuntimeConsoleAPICalledListener listener) {
        synchronized (this) {
            List<AbstractListener<?>> list = listeners.get(RuntimeConsoleAPICalled);
            if (list == null) {
                list = new ArrayList<>(1);
                listeners.put(RuntimeConsoleAPICalled, list);
            }
            return list.add(listener);
        }
    }

    public boolean addRuntimeInspectRequestedListener(RuntimeInspectRequestedListener listener) {
        synchronized (this) {
            List<AbstractListener<?>> list = listeners.get(RuntimeInspectRequested);
            if (list == null) {
                list = new ArrayList<>(1);
                listeners.put(RuntimeInspectRequested, list);
            }
            return list.add(listener);
        }
    }

    public boolean addDebuggerScriptParsedListener(DebuggerScriptParsedListener listener) {
        synchronized (this) {
            List<AbstractListener<?>> list = listeners.get(DebuggerScriptParsed);
            if (list == null) {
                list = new ArrayList<>(1);
                listeners.put(DebuggerScriptParsed, list);
            }
            return list.add(listener);
        }
    }

    public boolean addDebuggerScriptFailedToParseListener(DebuggerScriptFailedToParseListener listener) {
        synchronized (this) {
            List<AbstractListener<?>> list = listeners.get(DebuggerScriptFailedToParse);
            if (list == null) {
                list = new ArrayList<>(1);
                listeners.put(DebuggerScriptFailedToParse, list);
            }
            return list.add(listener);
        }
    }

    public boolean addDebuggerBreakpointResolvedListener(DebuggerBreakpointResolvedListener listener) {
        synchronized (this) {
            List<AbstractListener<?>> list = listeners.get(DebuggerBreakpointResolved);
            if (list == null) {
                list = new ArrayList<>(1);
                listeners.put(DebuggerBreakpointResolved, list);
            }
            return list.add(listener);
        }
    }

    public boolean addDebuggerPausedListener(DebuggerPausedListener listener) {
        synchronized (this) {
            List<AbstractListener<?>> list = listeners.get(DebuggerPaused);
            if (list == null) {
                list = new ArrayList<>(1);
                listeners.put(DebuggerPaused, list);
            }
            return list.add(listener);
        }
    }

    public boolean addDebuggerResumedListener(DebuggerResumedListener listener) {
        synchronized (this) {
            List<AbstractListener<?>> list = listeners.get(DebuggerResumed);
            if (list == null) {
                list = new ArrayList<>(1);
                listeners.put(DebuggerResumed, list);
            }
            return list.add(listener);
        }
    }

    public boolean addConsoleMessageAddedListener(ConsoleMessageAddedListener listener) {
        synchronized (this) {
            List<AbstractListener<?>> list = listeners.get(ConsoleMessageAdded);
            if (list == null) {
                list = new ArrayList<>(1);
                listeners.put(ConsoleMessageAdded, list);
            }
            return list.add(listener);
        }
    }

    public boolean addProfilerConsoleProfileStartedListener(ProfilerConsoleProfileStartedListener listener) {
        synchronized (this) {
            List<AbstractListener<?>> list = listeners.get(ProfilerConsoleProfileStarted);
            if (list == null) {
                list = new ArrayList<>(1);
                listeners.put(ProfilerConsoleProfileStarted, list);
            }
            return list.add(listener);
        }
    }

    public boolean addProfilerConsoleProfileFinishedListener(ProfilerConsoleProfileFinishedListener listener) {
        synchronized (this) {
            List<AbstractListener<?>> list = listeners.get(ProfilerConsoleProfileFinished);
            if (list == null) {
                list = new ArrayList<>(1);
                listeners.put(ProfilerConsoleProfileFinished, list);
            }
            return list.add(listener);
        }
    }

    public boolean addHeapProfilerAddHeapSnapshotChunkListener(HeapProfilerAddHeapSnapshotChunkListener listener) {
        synchronized (this) {
            List<AbstractListener<?>> list = listeners.get(HeapProfilerAddHeapSnapshotChunk);
            if (list == null) {
                list = new ArrayList<>(1);
                listeners.put(HeapProfilerAddHeapSnapshotChunk, list);
            }
            return list.add(listener);
        }
    }

    public boolean addHeapProfilerResetProfilesListener(HeapProfilerResetProfilesListener listener) {
        synchronized (this) {
            List<AbstractListener<?>> list = listeners.get(HeapProfilerResetProfiles);
            if (list == null) {
                list = new ArrayList<>(1);
                listeners.put(HeapProfilerResetProfiles, list);
            }
            return list.add(listener);
        }
    }

    public boolean addHeapProfilerReportHeapSnapshotProgressListener(
            HeapProfilerReportHeapSnapshotProgressListener listener) {
        synchronized (this) {
            List<AbstractListener<?>> list = listeners.get(HeapProfilerReportHeapSnapshotProgress);
            if (list == null) {
                list = new ArrayList<>(1);
                listeners.put(HeapProfilerReportHeapSnapshotProgress, list);
            }
            return list.add(listener);
        }
    }

    public boolean addHeapProfilerLastSeenObjectIdListener(HeapProfilerLastSeenObjectIdListener listener) {
        synchronized (this) {
            List<AbstractListener<?>> list = listeners.get(HeapProfilerLastSeenObjectId);
            if (list == null) {
                list = new ArrayList<>(1);
                listeners.put(HeapProfilerLastSeenObjectId, list);
            }
            return list.add(listener);
        }
    }

    public boolean addHeapProfilerHeapStatsUpdateListener(HeapProfilerHeapStatsUpdateListener listener) {
        synchronized (this) {
            List<AbstractListener<?>> list = listeners.get(HeapProfilerHeapStatsUpdate);
            if (list == null) {
                list = new ArrayList<>(1);
                listeners.put(HeapProfilerHeapStatsUpdate, list);
            }
            return list.add(listener);
        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public <T> void removeListener(AbstractListener<T> listener) {
        if (listener == null) {
            return;
        }
        synchronized (this) {
            Iterator<Entry<Events, List<AbstractListener<?>>>> iter = this.listeners.entrySet().iterator();
            while (iter.hasNext()) {
                List<AbstractListener<T>> removeList = new ArrayList<>(1);
                Entry<Events, List<AbstractListener<?>>> entry = iter.next();
                List<AbstractListener<?>> listeners = entry.getValue();
                for (AbstractListener next : listeners) {
                    if (next.equals(listener)) {
                        removeList.add(next);
                    }
                }
                if ( ! removeList.isEmpty() ) {
                    listeners.removeAll(removeList);
                    if (listeners.isEmpty()) {
                        this.listeners.remove(entry.getKey());
                    }
                }
                
            }
        }
    }
}
