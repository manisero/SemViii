using MBI.Logic.Entities;
using NUnit.Framework;

namespace MBI.Logic.Tests.ScaffoldBuilderTests
{
	[TestFixture]
	public class ScaffoldBuildingTests : ScaffoldBuilderTestsBase
	{
		[Test]
		[Sequential]
		public void inserts_gaps_properly(
			[Values(10, 20)] int petLength)
		{
			var pieces = new ScaffoldPiece[] { new Contig("aabbcc"), new Gap(petLength - 8), new Contig("ddeeff") };
			var pet = new PairedEndTag { Beginning = "bb", End = "ee", Length = petLength };

			TestBuild(new Scaffold { Pieces = pieces, Rank = 4 }, pet);
		}
	}
}
