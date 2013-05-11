using MBI.Logic.DNAAssemblance._Impl;
using MBI.Logic.Entities;
using NUnit.Framework;
using System.Linq;

namespace MBI.Logic.Tests.ScaffoldBuilderTests
{
	public class ScaffoldBuilderTestsBase
	{
		protected void TestBuild(string[] contigs, PairedEndTag pet, int expectedResult)
		{
			TestBuild(contigs, new[] { pet }, expectedResult);
		}

		protected void TestBuild(string[] contigs, PairedEndTag[] pets, int expectedResult)
		{
			// Act
			var result = new ScaffoldBuilder().Build(contigs.Select(x => new Contig(x)).ToArray(), pets);

			// Assert
			Assert.AreEqual(expectedResult, result);
		}
	}
}
