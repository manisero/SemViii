using System.Collections.Generic;
using MBI.Logic.DNAAssemblance;
using MBI.Logic.DNAAssemblance._Impl;
using NUnit.Framework;

namespace MBI.Logic.Tests.ScaffoldValidatorTests
{
	public class AssemblyValidatorTestsBase
	{
		protected void TestValidate(IEnumerable<string> contigs, IEnumerable<PairedEndTag> pets, int expectedResult)
		{
			// Act
			var result = new ScaffoldValidator().Validate(contigs, pets);

			// Assert
			Assert.AreEqual(expectedResult, result);
		}
	}
}
