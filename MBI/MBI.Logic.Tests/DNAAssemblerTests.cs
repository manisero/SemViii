using System.Collections.Generic;
using MBI.Logic.DNAAssemblance;
using MBI.Logic.DNAAssemblance._Impl;
using MBI.Logic.Tests.Extensions;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using System.Linq;
using Moq;

namespace MBI.Logic.Tests
{
	[TestClass]
	public class DNAAssemblerTests : TestsBase
	{
		[TestMethod]
		public void assigns_ranks_to_assemblies()
		{
			// Arrange
			var contigs = new[] { "aaa" };
			var pairedEndTags = new[] { new PairedEndTag() };
			var rank = 3;

			AutoMoqer.GetMock<IScaffoldValidator>().Setup(x => x.Validate(contigs, pairedEndTags)).Returns(rank);

			// Act
			var result = AutoMoqer.Resolve<DNAAssembler>().Assemble(contigs, pairedEndTags).ToList();

			// Assert
			Assert.IsNotNull(result);
			Assert.AreEqual(1, result.Count);
			Assert.AreEqual(rank, result[0].Rank);

			AutoMoqer.GetMock<IScaffoldValidator>().VerifyAll();
		}

		[TestMethod]
		public void rejects_assemblies_of_rank_0()
		{
			// Arrange
			var contigs = new[] { "aaa" };
			var pairedEndTags = new[] { new PairedEndTag() };

			AutoMoqer.GetMock<IScaffoldValidator>().Setup(x => x.Validate(contigs, pairedEndTags)).Returns(0);

			// Act
			var result = AutoMoqer.Resolve<DNAAssembler>().Assemble(contigs, pairedEndTags).ToList();

			// Assert
			Assert.IsNotNull(result);
			Assert.AreEqual(0, result.Count);

			AutoMoqer.GetMock<IScaffoldValidator>().VerifyAll();
		}

		[TestMethod]
		public void sorts_assemblies_by_rank()
		{
			// Arrange
			var contig1 = "aaa";
			var contig2 = "bbb";
			var contig3 = "ccc";
			var pairedEndTags = new[] { new PairedEndTag() };

			var assembly1 = new[] { contig2, contig1, contig3 };
			var assembly2 = new[] { contig1, contig3, contig2 };
			var assembly3 = new[] { contig3, contig2, contig1 };

			AutoMoqer.GetMock<IScaffoldValidator>().Setup(x => x.Validate(It.IsAny<IEnumerable<string>>(), pairedEndTags)).Returns(0);
			AutoMoqer.GetMock<IScaffoldValidator>().Setup(x => x.Validate(assembly1, pairedEndTags)).Returns(3);
			AutoMoqer.GetMock<IScaffoldValidator>().Setup(x => x.Validate(assembly2, pairedEndTags)).Returns(2);
			AutoMoqer.GetMock<IScaffoldValidator>().Setup(x => x.Validate(assembly3, pairedEndTags)).Returns(1);

			// Act
			var result = AutoMoqer.Resolve<DNAAssembler>().Assemble(new[] { contig1, contig2, contig3 }, pairedEndTags);

			// Assert
			Assert.IsNotNull(result);
			Assert.AreEqual(3, result.Count);
			AssertExtensions.AreEqual(assembly1, result[0].Contigs);
			AssertExtensions.AreEqual(assembly2, result[1].Contigs);
			AssertExtensions.AreEqual(assembly3, result[2].Contigs);
			
			AutoMoqer.GetMock<IScaffoldValidator>().VerifyAll();
		}

		[TestMethod]
		public void validates_all_contigs_combinations()
		{
			// Arrange
			var contig1 = "aaa";
			var contig2 = "bbb";
			var contig3 = "ccc";
			var pairedEndTags = new[] { new PairedEndTag() };

			AutoMoqer.GetMock<IScaffoldValidator>().Setup(x => x.Validate(new[] { contig1, contig2, contig3 }, pairedEndTags)).Returns(0);
			AutoMoqer.GetMock<IScaffoldValidator>().Setup(x => x.Validate(new[] { contig1, contig3, contig2 }, pairedEndTags)).Returns(0);
			AutoMoqer.GetMock<IScaffoldValidator>().Setup(x => x.Validate(new[] { contig2, contig1, contig3 }, pairedEndTags)).Returns(0);
			AutoMoqer.GetMock<IScaffoldValidator>().Setup(x => x.Validate(new[] { contig2, contig3, contig1 }, pairedEndTags)).Returns(0);
			AutoMoqer.GetMock<IScaffoldValidator>().Setup(x => x.Validate(new[] { contig3, contig1, contig2 }, pairedEndTags)).Returns(0);
			AutoMoqer.GetMock<IScaffoldValidator>().Setup(x => x.Validate(new[] { contig3, contig2, contig1 }, pairedEndTags)).Returns(0);

			// Act
			AutoMoqer.Resolve<DNAAssembler>().Assemble(new[] { contig1, contig2, contig3 }, pairedEndTags);

			// Assert
			AutoMoqer.GetMock<IScaffoldValidator>().VerifyAll();
		}
	}
}
