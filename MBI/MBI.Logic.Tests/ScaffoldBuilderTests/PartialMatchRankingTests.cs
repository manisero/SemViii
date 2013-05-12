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
			[Values("fff", "FFF")] string petEnd)
		{
			// Arrange
			var contigs = new[] { firstContig, secondConfig };
			var pet = new PairedEndTag { Beginning = petBeginning, End = petEnd, Length = 100 };

			// Act & Assert
			TestRank(contigs, pet, 4);
		}

		[Test]
		public void prefers_full_matches_rather_than_partial_matches()
		{
			// Arrange
			var contigs = new[] { "aabbaaacc", "ddeeeffee" };
			var pet = new PairedEndTag { Beginning = "aaa", End = "eee", Length = 100 };

			// Act & Assert
			TestRank(contigs, pet, 6);
		}

		[Test]
		[Sequential]
		public void matches_partial_beginnings_with_full_ends(
			[Values("XXbbcc", "aabbXX")] string firstContig)
		{
			// Arrange
			var contigs = new[] { firstContig, "ddeeff" };
			var pet = new PairedEndTag { Beginning = "XXX", End = "ee", Length = 100 };

			// Act & Assert
			TestRank(contigs, pet, 4);
		}

		[Test]
		[Sequential]
		public void matches_full_beginnings_with_partial_ends(
			[Values("XXeeff", "ddeeXX")] string secondContig)
		{
			// Arrange
			var contigs = new[] { "aabbcc", secondContig };
			var pet = new PairedEndTag { Beginning = "bb", End = "XXX", Length = 100 };

			// Act & Assert
			TestRank(contigs, pet, 4);
		}
	}
}
