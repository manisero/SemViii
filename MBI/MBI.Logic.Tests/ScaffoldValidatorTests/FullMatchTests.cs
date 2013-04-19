using MBI.Logic.DNAAssemblance;
using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace MBI.Logic.Tests.ScaffoldValidatorTests
{
	[TestClass]
	public class FullMatchTests : AssemblyValidatorTestsBase
	{
		[TestMethod]
		public void validates_two_contigs_based_on_one_PET()
		{
			// Arrange
			var contigs = new[] { "ddeeff", "aabbcc" };
			var pet = new PairedEndTag { Beginning = "bb", End = "ee", Length = 10 };

			// Act & Assert
			TestValidate(contigs, new[] { pet }, 4);
		}

		[TestMethod]
		public void validates_three_contigs_based_on_two_PETs()
		{
			// Arrange
			var contigs = new[] { "hhiijj", "ddeeffgg", "aabbcc" };
			var pet1 = new PairedEndTag { Beginning = "bb", End = "ff", Length = 10 };
			var pet2 = new PairedEndTag { Beginning = "ee", End = "ii", Length = 10 };

			// Act & Assert
			TestValidate(contigs, new[] { pet1, pet2 }, 6);
		}
	}
}
