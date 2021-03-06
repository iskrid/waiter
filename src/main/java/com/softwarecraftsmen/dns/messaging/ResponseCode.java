/**
 * This file is Copyright © 2008 Software Craftsmen Limited. All Rights Reserved.
 */
package com.softwarecraftsmen.dns.messaging;

import com.softwarecraftsmen.unsignedIntegers.Unsigned4BitInteger;
import com.softwarecraftsmen.unsignedIntegers.Unsigned16BitInteger;
import static com.softwarecraftsmen.unsignedIntegers.Unsigned4BitInteger.Zero;
import static com.softwarecraftsmen.unsignedIntegers.Unsigned4BitInteger.One;
import static com.softwarecraftsmen.unsignedIntegers.Unsigned4BitInteger.Two;
import static com.softwarecraftsmen.unsignedIntegers.Unsigned4BitInteger.Three;
import static com.softwarecraftsmen.unsignedIntegers.Unsigned4BitInteger.Four;
import static com.softwarecraftsmen.unsignedIntegers.Unsigned4BitInteger.Five;
import org.jetbrains.annotations.NotNull;

import static java.lang.String.format;
import static java.util.Locale.UK;

public enum ResponseCode
{
	NoErrorCondition(Zero, false),
	FormatError(One, true),
	ServerFailure(Two, true),
	NameError(Three, true),
	NotImplemented(Four, true),
	Refused(Five, true);

	private final Unsigned4BitInteger unsigned4BitInteger;
	private final boolean isError;

	private ResponseCode(final @NotNull Unsigned4BitInteger unsigned4BitInteger, final boolean isError)
	{
		this.unsigned4BitInteger = unsigned4BitInteger;
		this.isError = isError;
	}

	@NotNull
	public Unsigned16BitInteger set4Bits(final @NotNull Unsigned16BitInteger unsigned16BitInteger)
	{
		return unsigned16BitInteger.set4BitsIetf(unsigned4BitInteger, 4);
	}

	@NotNull
	public static ResponseCode responseCode(final Unsigned4BitInteger unsigned4BitInteger)
	{
		for (ResponseCode responseCode : values())
		{
			if (responseCode.unsigned4BitInteger.equals(unsigned4BitInteger))
			{
				return responseCode;
			}
		}
		throw new IllegalArgumentException(format(UK, "No ResponseCode known for %1$s", unsigned4BitInteger));
	}

	public boolean isError()
	{
		return isError;
	}
}
