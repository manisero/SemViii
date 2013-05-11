using MBI.Logic.DNAAssemblance._Impl;
using MBI.Logic.Entities;
using NUnit.Framework;

namespace MBI.Logic.Tests.ScaffoldValidatorTests
{
	public class ScaffoldValidatorTestsBase
	{
		protected void TestValidate(string[] contigs, PairedEndTag pet, int expectedResult)
		{
			// Act
			var result = new ScaffoldValidator().Validate(contigs, new[] { pet });

			// Assert
			Assert.AreEqual(expectedResult, result);
		}

		protected void TestValidate(string[] contigs, PairedEndTag[] pets, int expectedResult)
		{
			// Act
			var result = new ScaffoldValidator().Validate(contigs, pets);

			// Assert
			Assert.AreEqual(expectedResult, result);
		}
	}
}
