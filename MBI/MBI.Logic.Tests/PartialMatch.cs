using System.Linq;
using MBI.Logic._Impl;
using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace MBI.Logic.Tests
{
	[TestClass]
	public class PartialMatch
	{
		[TestMethod]
		public void assembles_two_contigs_with_two_possible_results()
		{
			// Arrange
			var contigs = new[] { "aabbcc", "ddeeff" };
			var pet1 = new PairedEndTag { Beginning = "bb", End = "ee", Length = 10 };
			var pet2 = new PairedEndTag { Beginning = "ee", End = "bb", Length = 10 };

			// Act
			var result = new DNAAssembler().Assemble(contigs, new[] { pet1, pet2 }).ToList();

			// Assert
			Assert.IsNotNull(result);
			Assert.AreEqual(2, result.Count);

			var assembly1 = result[0];
			Assert.AreEqual(4, assembly1.Rank);
			Assert.AreSame(contigs[0], assembly1.Contigs[0]);
			Assert.AreSame(contigs[1], assembly1.Contigs[1]);

			var assembly2 = result[1];
			Assert.AreEqual(4, assembly2.Rank);
			Assert.AreSame(contigs[1], assembly2.Contigs[0]);
			Assert.AreSame(contigs[0], assembly2.Contigs[1]);
		}
	}
}
