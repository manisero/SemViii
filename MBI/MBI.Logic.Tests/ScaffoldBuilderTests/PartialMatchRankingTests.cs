using MBI.Logic.Entities;
using NUnit.Framework;

namespace MBI.Logic.Tests.ScaffoldBuilderTests
{
	[TestFixture]
	public class PartialMatchRankingTests : ScaffoldBuilderTestsBase
	{
		[Test]
		[Sequential]
		public void finds_ends_of_pet_parts_in_beginnings_of_contigs(
			[Values("aabbcc", "AAbbcc")] string firstContig,
			[Values("ddeeff", "DDeeff")] string secondConfig,
			[Values("aaa", "AAA")] string petBeginning,
			[Values("ddd", "DDD")] string petEnd)
		{
			// Arrange
			var contigs = new[] { firstContig, secondConfig };
			var pet = new PairedEndTag { Beginning = petBeginning, End = petEnd, Length = 100 };

			// Act & Assert
			TestRank(contigs, pet, 4);
		}

		[Test]
		[Sequential]
		public void finds_beginnings_of_pet_parts_in_ends_of_contigs(
			[Values("aabbcc", "aabbCC")] string firstContig,
			[Values("ddeeff", "ddeeFF")] string secondConfig,
			[Values("ccc", "CCC")] string petBeginning,
			[Values("fff", "DDD")] string petEnd)
		{
			// Arrange
			var contigs = new[] { firstContig, secondConfig };
			var pet = new PairedEndTag { Beginning = petBeginning, End = petEnd, Length = 100 };

			// Act & Assert
			TestRank(contigs, pet, 4);
		}

		[Test]
		[Sequential]
		public void prefers_full_matches_rather_than_partial_matches(
			[Values("aabbaaacc")] string firstContig,
			[Values("ddeeeffee")] string secondConfig,
			[Values("aaa")] string petBeginning,
			[Values("eee")] string petEnd)
		{
			// Arrange
			var contigs = new[] { firstContig, secondConfig };
			var pet = new PairedEndTag { Beginning = petBeginning, End = petEnd, Length = 100 };

			// Act & Assert
			TestRank(contigs, pet, 6);
		}
	}
}
