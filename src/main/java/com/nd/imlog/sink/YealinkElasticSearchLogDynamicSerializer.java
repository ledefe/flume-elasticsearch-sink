package com.nd.imlog.sink;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.conf.ComponentConfiguration;
import org.apache.flume.sink.elasticsearch5.ContentBuilderUtil;
import org.apache.flume.sink.elasticsearch5.ElasticSearchEventSerializer;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;

public class YealinkElasticSearchLogDynamicSerializer implements ElasticSearchEventSerializer {

	private static final Logger logger = LoggerFactory.getLogger(YealinkElasticSearchLogDynamicSerializer.class);

	@Override
	public void configure(Context context) {
		// NO-OP...
	}

	@Override
	public void configure(ComponentConfiguration conf) {
		// NO-OP...
	}

	@Override
	public XContentBuilder getContentBuilder(Event event) throws IOException {
		XContentBuilder builder = jsonBuilder().startObject();
		try {

			appendBody(builder, event);

		} catch (JsonParseException e) {
			logger.error("Exception in jsonParseException", e);
			// fixed to JsonParseException
			ContentBuilderUtil.addSimpleField(builder, "@message", event.getBody());
		}
		try{
			appendHeaders(builder, event);
		} catch (Exception e) {
			logger.error("Exception in jsonParse with headers", e);
		}
		builder.endObject();
		return builder;
	}

	private void appendBody(XContentBuilder builder, Event event) throws IOException {
		ContentBuilderUtil.appendField(builder, "@message", event.getBody());
	}

	public final static DateTimeFormatter defaultDatePrinter = ISODateTimeFormat.dateTime().withZone(DateTimeZone.UTC);

	private void appendHeaders(XContentBuilder builder, Event event) throws IOException {
		Map<String, String> headers = event.getHeaders();
		for (String key : headers.keySet()) {

			ContentBuilderUtil.appendField(builder, key, headers.get(key).getBytes(charset));

			if (!StringUtils.isBlank(key)) {
				if (key.toLowerCase().equals("timestamp")) {
					ContentBuilderUtil.appendField(builder, "@timestamp",
							(ISODateTimeFormat.dateTime().print(Long.valueOf(headers.get(key)))).getBytes(charset));
				} else {
					ContentBuilderUtil.appendField(builder, key, headers.get(key).getBytes(charset));
				}
			}
		}
	}
}
