/**
 * This file is Copyright © 2008 Software Craftsmen Limited. All Rights Reserved.
 */
package com.softwarecraftsmen.dns.client.resourceRecordRepositories;

import com.softwarecraftsmen.dns.names.HostName;
import static com.softwarecraftsmen.dns.names.HostName.hostName;
import static com.softwarecraftsmen.dns.Seconds.seconds;
import com.softwarecraftsmen.dns.SerializableInternetProtocolAddress;
import static com.softwarecraftsmen.dns.SerializableInternetProtocolAddress.serializableInternetProtocolVersion4Address;
import com.softwarecraftsmen.dns.client.resolvers.MockDnsResolver;
import com.softwarecraftsmen.dns.messaging.InternetClassType;
import static com.softwarecraftsmen.dns.resourceRecords.CanonicalNameResourceRecord.canonicalNameResourceRecord;
import static com.softwarecraftsmen.dns.resourceRecords.InternetProtocolVersion4AddressResourceRecord.internetProtocolVersion4AddressResourceRecord;
import org.hamcrest.Matcher;
import static org.hamcrest.Matchers.hasItems;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;

import java.net.Inet4Address;

public class NonCachingResourceRecordRepositoryTest
{
	private static final HostName CanonicalName = hostName("www.l.google.com");
	private static final HostName AliasName = hostName("www.google.com");

	@Test
	public void canSelectMultipleRecordsOfTheSameTypeFromDifferentTypes()
	{
		final SerializableInternetProtocolAddress<Inet4Address> address1 = serializableInternetProtocolVersion4Address(1, 2, 3, 4);
		final SerializableInternetProtocolAddress<Inet4Address> address2 = serializableInternetProtocolVersion4Address(2, 2, 3, 4);
		dnsResolver.program(internetProtocolVersion4AddressResourceRecord(CanonicalName, seconds(1000), address1));
		dnsResolver.program(internetProtocolVersion4AddressResourceRecord(CanonicalName, seconds(1000), address2));
		dnsResolver.program(canonicalNameResourceRecord(AliasName, seconds(1000), CanonicalName));
		final Iterable<SerializableInternetProtocolAddress<Inet4Address>> data = nonCachingResourceRecordRepository.findData(AliasName, InternetClassType.A);
		final Matcher<Iterable<SerializableInternetProtocolAddress<Inet4Address>>> iterableMatcher = hasItems(address1, address2);
		assertThat(data, iterableMatcher);
	}

	@Before
	public void before()
	{
		dnsResolver = new MockDnsResolver();
		nonCachingResourceRecordRepository = new NonCachingResourceRecordRepository(dnsResolver);
	}

	private MockDnsResolver dnsResolver;
	private NonCachingResourceRecordRepository nonCachingResourceRecordRepository;
}
