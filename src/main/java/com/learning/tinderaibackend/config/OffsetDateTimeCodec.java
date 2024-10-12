package com.learning.tinderaibackend.config;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Date;

import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

public class OffsetDateTimeCodec implements Codec<OffsetDateTime> {
    // NOT IN USE
    @Override
    public OffsetDateTime decode(BsonReader reader, DecoderContext decoderContext) {
        Date date = new Date(reader.readDateTime());
        return OffsetDateTime.ofInstant(date.toInstant(), ZoneOffset.UTC);
    }

    @Override
    public void encode(BsonWriter writer, OffsetDateTime value, EncoderContext encoderContext) {
        writer.writeDateTime(value.toInstant().toEpochMilli());
    }

    @Override
    public Class<OffsetDateTime> getEncoderClass() {
        return OffsetDateTime.class;
    }
}
