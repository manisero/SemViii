﻿using System;
using System.Collections.Generic;
using System.Linq;
using NUnit.Framework;

namespace MBI.Logic.Tests.Extensions
{
	public static class AssertExtensions
	{
		public static bool AreEqual<T1, T2>(IEnumerable<T1> expected, IEnumerable<T2> actual)
		{
			var expectedCount = expected.Count();

			Assert.AreEqual(expectedCount, actual.Count());

			for (int i = 0; i < expectedCount; i++)
			{
				Assert.AreEqual(expected.ElementAt(i), actual.ElementAt(i));
			}

			return true;
		}

		public static bool AreEqual<T>(IEnumerable<T> expected, IEnumerable<T> actual, Action<T, T> itemAssertion)
		{
			var expectedCount = expected.Count();

			Assert.AreEqual(expectedCount, actual.Count());

			for (int i = 0; i < expectedCount; i++)
			{
				var expectedItem = expected.ElementAt(i);
				var actualItem = actual.ElementAt(i);

				itemAssertion(expectedItem, actualItem);
			}

			return true;
		}
	}
}
