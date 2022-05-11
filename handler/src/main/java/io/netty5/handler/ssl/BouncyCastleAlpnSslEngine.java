/*
 * Copyright 2021 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package io.netty5.handler.ssl;

import javax.net.ssl.SSLEngine;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

final class BouncyCastleAlpnSslEngine extends JdkAlpnSslEngine {

    BouncyCastleAlpnSslEngine(SSLEngine engine,
                     @SuppressWarnings("deprecation") JdkApplicationProtocolNegotiator applicationNegotiator,
                     boolean isServer) {
        super(engine, applicationNegotiator, isServer,
                new BiConsumer<>() {
                    @Override
                    public void accept(SSLEngine e, AlpnSelector s) {
                        BouncyCastleAlpnSslUtils.setHandshakeApplicationProtocolSelector(e, s);
                    }
                },
                new BiConsumer<>() {
                    @Override
                    public void accept(SSLEngine e, List<String> p) {
                        BouncyCastleAlpnSslUtils.setApplicationProtocols(e, p);
                    }
                });
    }

    @Override
    public String getApplicationProtocol() {
        return BouncyCastleAlpnSslUtils.getApplicationProtocol(getWrappedEngine());
    }

    @Override
    public String getHandshakeApplicationProtocol() {
        return BouncyCastleAlpnSslUtils.getHandshakeApplicationProtocol(getWrappedEngine());
    }

    @Override
    public void setHandshakeApplicationProtocolSelector(BiFunction<SSLEngine, List<String>, String> selector) {
        BouncyCastleAlpnSslUtils.setHandshakeApplicationProtocolSelector(getWrappedEngine(), selector);
    }

    @Override
    public BiFunction<SSLEngine, List<String>, String> getHandshakeApplicationProtocolSelector() {
        return BouncyCastleAlpnSslUtils.getHandshakeApplicationProtocolSelector(getWrappedEngine());
    }

}