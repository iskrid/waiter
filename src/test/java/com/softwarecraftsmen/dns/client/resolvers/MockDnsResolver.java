/**
 * This file is Copyright © 2008 Software Craftsmen Limited. All Rights Reserved.
 */
package com.softwarecraftsmen.dns.client.resolvers;

import com.softwarecraftsmen.dns.names.Name;
import com.softwarecraftsmen.dns.messaging.InternetClassType;
import com.softwarecraftsmen.dns.messaging.Message;
import com.softwarecraftsmen.dns.messaging.MessageHeader;
import com.softwarecraftsmen.dns.messaging.Question;
import com.softwarecraftsmen.dns.messaging.serializer.Serializable;
import com.softwarecraftsmen.dns.resourceRecords.ResourceRecord;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import org.jetbrains.annotations.NotNull;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

public final class MockDnsResolver implements DnsResolver
{
	private List<ResourceRecord<? extends Name, ? extends Serializable>> resourceRecords;
	private int resolvedCalledCount;

	public MockDnsResolver()
	{
		resourceRecords = new ArrayList<ResourceRecord<? extends Name, ? extends Serializable>>();
		resolvedCalledCount = 0;
	}

	public void program(final @NotNull ResourceRecord<? extends Name, ? extends Serializable> resourceRecord)
	{
		resourceRecords.add(resourceRecord);
	}

	@NotNull
	public List<ResourceRecord<? extends Name, ? extends Serializable>> findAllMatchingRecords()
	{
		return null;
	}

	public void assertResolveCalledOnceOnly()
	{
		assertThat(resolvedCalledCount, is(equalTo(1)));
	}

	public void assertResolveCalledTwice()
	{
		assertThat(resolvedCalledCount, is(equalTo(2)));
	}

	@NotNull
	public Message resolve(final @NotNull Name name, final @NotNull InternetClassType internetClassType)
	{
		resolvedCalledCount++;
		final MessageHeader messageHeader = new MessageHeader(com.softwarecraftsmen.dns.messaging.MessageId.messageId(), com.softwarecraftsmen.dns.messaging.MessageHeaderFlags.reply(true), com.softwarecraftsmen.unsignedIntegers.Unsigned16BitInteger.Zero, com.softwarecraftsmen.unsignedIntegers.Unsigned16BitInteger.unsigned16BitInteger(resourceRecords.size()), com.softwarecraftsmen.unsignedIntegers.Unsigned16BitInteger.Zero, com.softwarecraftsmen.unsignedIntegers.Unsigned16BitInteger.Zero);
		return new Message(messageHeader, new ArrayList<Question>(), resourceRecords, com.softwarecraftsmen.dns.messaging.Message.NoResourceRecords, com.softwarecraftsmen.dns.messaging.Message.NoResourceRecords);
	}
}
