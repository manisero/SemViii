using MBI.Logic.DNAAssemblance;
using MBI.Logic.DNAAssemblance._Impl;
using MBI.Logic.Entities;
using MBI.Logic.Tests.Extensions;
using System.Linq;
using NUnit.Framework;
using Rhino.Mocks;

namespace MBI.Logic.Tests
{
	[TestFixture]
	public class DNAAssemblerTests
	{
		private IScaffoldBuilder _scaffoldBuilderMock;
		private DNAAssembler _dnaAssembler;

		[SetUp]
		public void SetUp()
		{
			_scaffoldBuilderMock = MockRepository.GenerateStrictMock<IScaffoldBuilder>();
			_dnaAssembler = new DNAAssembler(_scaffoldBuilderMock);
		}

		[Test]
		public void assigns_ranks_to_assemblies()
		{
			// Arrange
			var contigs = new[] { new Contig("aaa") };
			var pairedEndTags = new[] { new PairedEndTag() };
			var rank = 3;

			_scaffoldBuilderMock.Expect(x => x.Build(contigs, pairedEndTags)).Return(rank);

			// Act
			var result = _dnaAssembler.Assemble(contigs, pairedEndTags).ToList();

			// Assert
			Assert.IsNotNull(result);
			Assert.AreEqual(1, result.Count);
			Assert.AreEqual(rank, result[0].Rank);

			_scaffoldBuilderMock.VerifyAllExpectations();
		}

		[Test]
		public void rejects_assemblies_of_rank_0()
		{
			// Arrange
			var contigs = new[] { new Contig("aaa") };
			var pairedEndTags = new[] { new PairedEndTag() };

			_scaffoldBuilderMock.Expect(x => x.Build(contigs, pairedEndTags)).Return(0);

			// Act
			var result = _dnaAssembler.Assemble(contigs, pairedEndTags).ToList();

			// Assert
			Assert.IsNotNull(result);
			Assert.AreEqual(0, result.Count);

			_scaffoldBuilderMock.VerifyAllExpectations();
		}

		[Test]
		public void sorts_assemblies_by_rank()
		{
			// Arrange
			var contig1 = new Contig("aaa");
			var contig2 = new Contig("bbb");
			var contig3 = new Contig("ccc");
			var pairedEndTags = new[] { new PairedEndTag() };

			var assembly1 = new[] { contig2, contig1, contig3 };
			var assembly2 = new[] { contig1, contig3, contig2 };
			var assembly3 = new[] { contig3, contig2, contig1 };

			_scaffoldBuilderMock.Expect(x => x.Build(assembly1, pairedEndTags)).Return(3);
			_scaffoldBuilderMock.Expect(x => x.Build(assembly2, pairedEndTags)).Return(2);
			_scaffoldBuilderMock.Expect(x => x.Build(assembly3, pairedEndTags)).Return(1);
			_scaffoldBuilderMock.Expect(x => x.Build(Arg<Contig[]>.Is.Anything, Arg<PairedEndTag[]>.Is.Equal(pairedEndTags))).Return(0);
			
			// Act
			var result = _dnaAssembler.Assemble(new[] { contig1, contig2, contig3 }, pairedEndTags);

			// Assert
			Assert.IsNotNull(result);
			Assert.AreEqual(3, result.Count);
			AssertExtensions.AreEqual(assembly1, result[0].Pieces);
			AssertExtensions.AreEqual(assembly2, result[1].Pieces);
			AssertExtensions.AreEqual(assembly3, result[2].Pieces);

			_scaffoldBuilderMock.VerifyAllExpectations();
		}

		[Test]
		public void validates_all_contigs_combinations()
		{
			// Arrange
			var contig1 = new Contig("aaa");
			var contig2 = new Contig("bbb");
			var contig3 = new Contig("ccc");
			var pairedEndTags = new[] { new PairedEndTag() };

			_scaffoldBuilderMock.Expect(x => x.Build(new[] { contig1, contig2, contig3 }, pairedEndTags)).Return(0);
			_scaffoldBuilderMock.Expect(x => x.Build(new[] { contig1, contig3, contig2 }, pairedEndTags)).Return(0);
			_scaffoldBuilderMock.Expect(x => x.Build(new[] { contig2, contig1, contig3 }, pairedEndTags)).Return(0);
			_scaffoldBuilderMock.Expect(x => x.Build(new[] { contig2, contig3, contig1 }, pairedEndTags)).Return(0);
			_scaffoldBuilderMock.Expect(x => x.Build(new[] { contig3, contig1, contig2 }, pairedEndTags)).Return(0);
			_scaffoldBuilderMock.Expect(x => x.Build(new[] { contig3, contig2, contig1 }, pairedEndTags)).Return(0);

			// Act
			_dnaAssembler.Assemble(new[] { contig1, contig2, contig3 }, pairedEndTags);

			// Assert
			_scaffoldBuilderMock.VerifyAllExpectations();
		}
	}
}
