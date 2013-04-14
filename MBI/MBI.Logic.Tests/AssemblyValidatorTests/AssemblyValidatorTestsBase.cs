using System.Collections.Generic;
using MBI.Logic.DNAAssemblance;
using MBI.Logic.DNAAssemblance._Impl;
using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace MBI.Logic.Tests.AssemblyValidatorTests
{
	public class AssemblyValidatorTestsBase
	{
		protected void TestValidate(IEnumerable<string> contigs, IEnumerable<PairedEndTag> pets, int expectedResult)
		{
			// Act
			var result = new AssemblyValidator().Validate(contigs, pets);

			// Assert
			Assert.AreEqual(expectedResult, result);
		}
	}
}
