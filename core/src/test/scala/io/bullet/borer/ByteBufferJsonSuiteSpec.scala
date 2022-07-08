/*
 * Copyright (c) 2019-2022 Mathias Doenitz
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package io.bullet.borer

import java.nio.charset.StandardCharsets
import java.nio.ByteBuffer

class ByteBufferJsonSuiteSpec extends AbstractJsonSuiteSpec:

  def encode[T: Encoder](value: T): String =
    val byteBuffer = Json.encode(value).withConfig(Json.EncodingConfig(bufferSize = 8)).toByteBuffer
    new String(ByteAccess.ForByteBuffer.toByteArray(byteBuffer), StandardCharsets.UTF_8)

  def decode[T: Decoder](encoded: String): T =
    Json
      .decode(ByteBuffer.wrap(encoded getBytes StandardCharsets.UTF_8))
      .withConfig(Json.DecodingConfig.default.copy(maxNumberAbsExponent = 300))
      .to[T]
      .value
