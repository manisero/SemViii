using MBI.Logic.Entities;
using NUnit.Framework;

namespace MBI.Logic.Tests.ScaffoldBuilderTests
{
	[TestFixture]
	public class NoMatchRankingTests : ScaffoldBuilderTestsBase
	{
		[Test]
		public void accepts_not_paired_beginning_in_last_contig()
		{
			// Arrange
			var contigs = new[] { "aabbcc", "ddeeff" };
			var pet = new PairedEndTag { Beginning = "ee", End = "zz", Length = 10 };

			// Act * Assert
			TestRank(contigs, pet, 2);
		}

		[Test]
		public void accepts_not_paired_end_in_first_contig()
		{
			// Arrange
			var contigs = new[] { "aabbcc", "ddeeff" };
			var pet = new PairedEndTag { Beginning = "zz", End = "bb", Length = 10 };

			// Act * Assert
			TestRank(contigs, pet, 2);
		}

		[Test]
		public void accepts_partially_matching_not_paired_beginning_in_last_contig(
			[Values("XXeeff", "ddeeXX")] string lastContig)
		{
			// Arrange
			var contigs = new[] { "aabbcc", lastContig };
			var pet = new PairedEndTag { Beginning = "XXX", End = "zz", Length = 10 };

			// Act * Assert
			TestRank(contigs, pet, 2);
		}

		[Test]
		public void accepts_partially_matching_not_paired_end_in_first_contig(
			[Values("XXbbcc", "aabbXX")] string firstContig)
		{
			// Arrange
			var contigs = new[] { firstContig, "ddeeff" };
			var pet = new PairedEndTag { Beginning = "zz", End = "XXX", Length = 10 };

			// Act * Assert
			TestRank(contigs, pet, 2);
		}

		[Test]
		public void rejects_not_paired_beginning(
			[Values("aaccdd", "aabBcc", "aaBbcc", "aabcbc")] string contig)
		{
			// Arrange
			var contigs = new[] { contig, "xxyyzz" };
			var pet = new PairedEndTag { Beginning = "bb", End = "yy", Length = 1000 };

			// Act * Assert
			TestRank(contigs, pet, 0);
		}

		[Test]
		public void rejects_not_paired_end(
			[Values("wwxxzz", "xxyYzz", "xxYyzz", "xyxyzz")] string contig)
		{
			// Arrange
			var contigs = new[] { "aabbcc", contig };
			var pet = new PairedEndTag { Beginning = "bb", End = "yy", Length = 1000 };

			// Act * Assert
			TestRank(contigs, pet, 0);
		}
	}
}
