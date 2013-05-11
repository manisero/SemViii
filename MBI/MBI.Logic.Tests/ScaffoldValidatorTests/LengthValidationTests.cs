using NUnit.Framework;

namespace MBI.Logic.Tests.ScaffoldValidatorTests
{
	[TestFixture]
	public class LengthValidationTests : ScaffoldValidatorTestsBase
	{
		[Test]
		[Sequential]
		public void validates_pet_length(
			[Values(10, 5)] int petLength,
			[Values(4, 0)] int expectedResult)
		{
			// Arrange
			var contigs = new[] { "aabbcc", "ddeeff" };
			var pet = new PairedEndTag { Beginning = "bb", End = "ee", Length = petLength };

			// Act & Assert
			TestValidate(contigs, pet, expectedResult);
		}

		[Test]
		public void rejects_two_pets_of_different_lengths_placed_in_the_same_configs()
		{
			// Arrange
			var contigs = new[] { "aabb", "ccdd" };
			var pet1 = new PairedEndTag { Beginning = "aa", End = "cc", Length = 10 };
			var pet2 = new PairedEndTag { Beginning = "bb", End = "dd", Length = 20 };

			// Act & Assert
			TestValidate(contigs, new[] { pet1, pet2 }, 4);
		}
	}
}
