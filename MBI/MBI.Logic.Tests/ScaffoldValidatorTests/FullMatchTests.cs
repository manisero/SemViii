using MBI.Logic.DNAAssemblance;
using NUnit.Framework;

namespace MBI.Logic.Tests.ScaffoldValidatorTests
{
	[TestFixture]
	public class FullMatchTests : AssemblyValidatorTestsBase
	{
		[Test]
		public void validates_two_contigs_based_on_one_PET()
		{
			// Arrange
			var contigs = new[] { "aabbcc", "ddeeff"  };
			var pet = new PairedEndTag { Beginning = "bb", End = "ee", Length = 10 };

			// Act & Assert
			TestValidate(contigs, new[] { pet }, 4);
		}

		[Test]
		public void validates_three_contigs_based_on_two_PETs()
		{
			// Arrange
			var contigs = new[] { "aabbcc", "ddeeffgg", "hhiijj" };
			var pet1 = new PairedEndTag { Beginning = "bb", End = "ff", Length = 10 };
			var pet2 = new PairedEndTag { Beginning = "ee", End = "ii", Length = 10 };

			// Act & Assert
			TestValidate(contigs, new[] { pet1, pet2 }, 8);
		}
	}
}
