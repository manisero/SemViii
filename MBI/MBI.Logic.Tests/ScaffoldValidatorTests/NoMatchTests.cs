using MBI.Logic.DNAAssemblance;
using NUnit.Framework;

namespace MBI.Logic.Tests.ScaffoldValidatorTests
{
	[TestFixture]
	public class NoMatchTests : ScaffoldValidatorTestsBase
	{
		[Test]
		public void accepts_not_paired_beginning_in_last_contig()
		{
			// Arrange
			var contigs = new[] { "aabbcc", "ddeeff" };
			var pet = new PairedEndTag { Beginning = "ee", End = "zz", Length = 10 };

			// Act * Assert
			TestValidate(contigs, pet, 2);
		}

		[Test]
		public void accepts_not_paired_end_in_first_contig()
		{
			// Arrange
			var contigs = new[] { "aabbcc", "ddeeff" };
			var pet = new PairedEndTag { Beginning = "zz", End = "bb", Length = 10 };

			// Act * Assert
			TestValidate(contigs, pet, 2);
		}
	}
}
