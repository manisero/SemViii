using MBI.Logic.Entities;
using NUnit.Framework;

namespace MBI.Logic.Tests.ScaffoldBuilderTests
{
	[TestFixture]
	public class ScaffoldBuildingTests : ScaffoldBuilderTestsBase
	{
		[Test]
		public void inserts_gaps_properly_for_two_contigs_and_one_pet(
			[Values(10, 20)] int petLength)
		{
			var pieces = new ScaffoldPiece[] { new Contig("aabbcc"), new Gap(petLength - 8), new Contig("ddeeff") };
			var pet = new PairedEndTag { Beginning = "bb", End = "ee", Length = petLength };

			TestBuild(new Scaffold { Pieces = pieces, Rank = 4 }, pet);
		}

		[Test]
		public void inserts_gaps_properly_for_three_contigs_and_two_pets(
			[Values(14, 10)] int gap1Length,
			[Values(19, 26)] int gap2Legth)
		{
			var pieces = new ScaffoldPiece[] { new Contig("aabbcc"), new Gap(gap1Length - 10), new Contig("ddeeffgg"), new Gap(gap2Legth - 14), new Contig("hhiijjkkll") };
			var pet1 = new PairedEndTag { Beginning = "ee", End = "kk", Length = gap2Legth };
			var pet2 = new PairedEndTag { Beginning = "bb", End = "ff", Length = gap1Length };

			TestBuild(new Scaffold { Pieces = pieces, Rank = 8 }, pet1, pet2);
		}
	}
}
