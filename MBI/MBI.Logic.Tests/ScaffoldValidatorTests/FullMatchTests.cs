using MBI.Logic.Entities;
using NUnit.Framework;

namespace MBI.Logic.Tests.ScaffoldValidatorTests
{
	[TestFixture]
	public class FullMatchTests : ScaffoldValidatorTestsBase
	{
		[Test]
		[Sequential]
		public void validates_two_contigs_based_on_one_PET(
			[Values("aabbcc", "abbbcc", "aaBBcc")] string firstContig,
			[Values("ddeeff", "ddeeef", "ddEEff")] string secondConfig,
			[Values("bb", "bb", "BB")] string petBeginning,
			[Values("ee", "ee", "EE")] string petEnd)
		{
			// Arrange
			var contigs = new[] { firstContig, secondConfig  };
			var pet = new PairedEndTag { Beginning = petBeginning, End = petEnd, Length = 100 };

			// Act & Assert
			TestValidate(contigs, pet, 4);
		}

		[Test]
		[Sequential]
		public void validates_three_contigs_based_on_two_PETs(
			[Values("aabbcc", "abbbcc", "aaBBcc", "aabbcc")] string firstContig,
			[Values("ddeeffgg", "deeefffg", "ddEEFFgg", "ddeeffgg")] string secondContig,
			[Values("hhiijj", "hhiiij", "hhIIjj", "hhiijj")] string thirdContig,
			[Values("bb", "bb", "BB", "bb")] string firstPetBeginning,
			[Values("ee", "ee", "EE", "ff")] string firstPetEnd,
			[Values("ff", "ff", "FF", "ee")] string secondPetBeginning,
			[Values("ii", "ii", "II", "ii")] string secondPetEnd)
		{
			// Arrange
			var contigs = new[] { firstContig, secondContig, thirdContig };
			var pet1 = new PairedEndTag { Beginning = firstPetBeginning, End = firstPetEnd, Length = 100 };
			var pet2 = new PairedEndTag { Beginning = secondPetBeginning, End = secondPetEnd, Length = 100 };

			// Act & Assert
			TestValidate(contigs, new[] { pet1, pet2 }, 8);
		}

		[Test]
		[Sequential]
		public void validates_all_beginning_occurences_in_contig(
			[Values(12, 8)] int petLength,
			[Values(4, 4)] int expectedResult)
		{
			// Arrange
			var contigs = new[] { "aabbccbbdd", "eeffgg" };
			var pet = new PairedEndTag { Beginning = "bb", End = "ff", Length = petLength };

			// Act & Assert
			TestValidate(contigs, pet, expectedResult);
		}

		[Test]
		[Sequential]
		public void validates_all_end_occurences_in_contig(
			[Values(12, 8)] int petLength,
			[Values(4, 4)] int expectedResult)
		{
			// Arrange
			var contigs = new[] { "aabbcc", "ddeeffeegg" };
			var pet = new PairedEndTag { Beginning = "bb", End = "ee", Length = petLength };

			// Act & Assert
			TestValidate(contigs, pet, expectedResult);
		}

		[Test]
		[Sequential]
		public void handles_pet_longer_than_one_conting(
			[Values(20, 14, 12)] int petLength,
			[Values(4, 4, 0)] int expectedResult)
		{
			// Arrange
			var contigs = new[] { "aabbcc", "ddeeff", "gghhii" };
			var pet = new PairedEndTag { Beginning = "bb", End = "hh", Length = petLength };

			// Act & Assert
			TestValidate(contigs, pet, expectedResult);
		}
	}
}
