using MBI.Logic.DNAAssemblance._Impl;
using MBI.Logic.Entities;
using MBI.Logic.Tests.Extensions;
using NUnit.Framework;
using System.Linq;

namespace MBI.Logic.Tests.ScaffoldBuilderTests
{
	public class ScaffoldBuilderTestsBase
	{
		protected void TestBuild(string[] contigs, PairedEndTag pet, int expectedRank)
		{
			TestBuild(contigs, new[] { pet }, expectedRank);
		}

		protected void TestBuild(string[] contigs, PairedEndTag[] pets, int expectedRank)
		{
			var expectedScaffold = new Scaffold { Rank = expectedRank };
			expectedScaffold.Pieces = contigs.Select(x => new Contig(x)).Cast<ScaffoldPiece>().ToArray();

			TestBuild(contigs, pets, expectedScaffold);
		}

		protected void TestBuild(string[] contigs, PairedEndTag[] pets, Scaffold expectedScaffold)
		{
			// Act
			var result = new ScaffoldBuilder().Build(contigs.Select(x => new Contig(x)).ToArray(), pets);

			// Assert
			Assert.IsNotNull(result);
			Assert.AreEqual(expectedScaffold.Rank, result.Rank);
		}
	}
}
