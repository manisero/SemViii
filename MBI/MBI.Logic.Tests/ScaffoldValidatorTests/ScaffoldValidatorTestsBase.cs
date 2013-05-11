using MBI.Logic.DNAAssemblance._Impl;
using MBI.Logic.Entities;
using NUnit.Framework;
using System.Linq;

namespace MBI.Logic.Tests.ScaffoldValidatorTests
{
	public class ScaffoldValidatorTestsBase
	{
		protected void TestValidate(string[] contigs, PairedEndTag pet, int expectedResult)
		{
			TestValidate(contigs, new[] { pet }, expectedResult);
		}

		protected void TestValidate(string[] contigs, PairedEndTag[] pets, int expectedResult)
		{
			// Act
			var result = new ScaffoldValidator().Validate(contigs.Select(x => new Contig(x)).ToArray(), pets);

			// Assert
			Assert.AreEqual(expectedResult, result);
		}
	}
}
