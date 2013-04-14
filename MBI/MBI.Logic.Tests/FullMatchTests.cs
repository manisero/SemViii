using MBI.Logic._Impl;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using System.Linq;

namespace MBI.Logic.Tests
{
	[TestClass]
	public class FullMatchTests
	{
		[TestMethod]
		public void assembles_two_contigs_based_on_one_PET()
		{
			// Arrange
			var contigs = new[] { "ddeeff", "aabbcc" };
			var pet = new PairedEndTag { Beginning = "bb", End = "ee", Length = 10 };

			// Act
			var result = new DNAAssembler().Assemble(contigs, new[] { pet }).ToList();

			// Assert
			Assert.IsNotNull(result);
			Assert.AreEqual(1, result.Count);

			var assembly = result[0];
			Assert.AreEqual(4, assembly.Rank);
			Assert.AreSame(contigs[1], assembly.Contigs[0]);
			Assert.AreSame(contigs[0], assembly.Contigs[1]);
		}

		[TestMethod]
		public void assembles_three_contigs_based_on_two_PETs()
		{
			// Arrange
			var contigs = new[] { "hhiijj", "ddeeffgg", "aabbcc" };
			var pet1 = new PairedEndTag { Beginning = "bb", End = "ff", Length = 10 };
			var pet2 = new PairedEndTag { Beginning = "ee", End = "ii", Length = 10 };

			// Act
			var result = new DNAAssembler().Assemble(contigs, new[] { pet1, pet2 }).ToList();

			// Assert
			Assert.IsNotNull(result);
			Assert.AreEqual(1, result.Count);

			var assembly = result[0];
			Assert.AreEqual(6, assembly.Rank);
			Assert.AreSame(contigs[2], assembly.Contigs[0]);
			Assert.AreSame(contigs[1], assembly.Contigs[1]);
			Assert.AreSame(contigs[0], assembly.Contigs[2]);
		}
	}
}
