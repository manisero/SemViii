﻿using MBI.Logic.DNAAssemblance;
using MBI.Logic.DNAAssemblance._Impl;
using MBI.Logic.Entities;
using System.Linq;
using NUnit.Framework;
using Rhino.Mocks;

namespace MBI.Logic.Tests
{
	[TestFixture]
	public class DNAAssemblerTests
	{
		private IAssemblyFilter _assemblyFilterMock;
		private IScaffoldBuilder _scaffoldBuilderMock;
		private DNAAssembler _dnaAssembler;

		[SetUp]
		public void SetUp()
		{
			_assemblyFilterMock = MockRepository.GenerateStrictMock<IAssemblyFilter>();
			_scaffoldBuilderMock = MockRepository.GenerateStrictMock<IScaffoldBuilder>();
			_dnaAssembler = new DNAAssembler(_assemblyFilterMock, _scaffoldBuilderMock);
		}

		[Test]
		public void rejects_assemblies_of_rank_0()
		{
			// Arrange
			var contig1 = new Contig("aaa");
			var contig2 = new Contig("bbb");
			var contigs_accepted = new[] { contig1, contig2 };
			var contigs_rejected = new[] { contig2, contig1 };
			var pairedEndTags = new[] { new PairedEndTag() };

			var scaffold_accepted = new Scaffold { Rank = 10 };
			var scaffold_rejected = new Scaffold { Rank = 0 };

			_scaffoldBuilderMock.Expect(x => x.Build(contigs_accepted, pairedEndTags)).Return(scaffold_accepted);
			_scaffoldBuilderMock.Expect(x => x.Build(contigs_rejected, pairedEndTags)).Return(scaffold_rejected);

			// Act
			var result = _dnaAssembler.Assemble(new[] { contig1, contig2 }, pairedEndTags).ToList();

			// Assert
			Assert.IsNotNull(result);
			Assert.AreEqual(1, result.Count);

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

			var scaffold1 = new Scaffold { Rank = 3 };
			var scaffold2 = new Scaffold { Rank = 2 };
			var scaffold3 = new Scaffold { Rank = 1 };

			_scaffoldBuilderMock.Expect(x => x.Build(assembly1, pairedEndTags)).Return(scaffold1);
			_scaffoldBuilderMock.Expect(x => x.Build(assembly2, pairedEndTags)).Return(scaffold2);
			_scaffoldBuilderMock.Expect(x => x.Build(assembly3, pairedEndTags)).Return(scaffold3);
			_scaffoldBuilderMock.Expect(x => x.Build(Arg<Contig[]>.Is.Anything, Arg<PairedEndTag[]>.Is.Equal(pairedEndTags))).Return(new Scaffold { Rank = 0 });
			
			// Act
			var result = _dnaAssembler.Assemble(new[] { contig1, contig2, contig3 }, pairedEndTags);

			// Assert
			Assert.IsNotNull(result);
			Assert.AreEqual(3, result.Count);
			Assert.AreEqual(scaffold1, result[0]);
			Assert.AreEqual(scaffold2, result[1]);
			Assert.AreEqual(scaffold3, result[2]);

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

			_scaffoldBuilderMock.Expect(x => x.Build(new[] { contig1, contig2, contig3 }, pairedEndTags)).Return(new Scaffold());
			_scaffoldBuilderMock.Expect(x => x.Build(new[] { contig1, contig3, contig2 }, pairedEndTags)).Return(new Scaffold());
			_scaffoldBuilderMock.Expect(x => x.Build(new[] { contig2, contig1, contig3 }, pairedEndTags)).Return(new Scaffold());
			_scaffoldBuilderMock.Expect(x => x.Build(new[] { contig2, contig3, contig1 }, pairedEndTags)).Return(new Scaffold());
			_scaffoldBuilderMock.Expect(x => x.Build(new[] { contig3, contig1, contig2 }, pairedEndTags)).Return(new Scaffold());
			_scaffoldBuilderMock.Expect(x => x.Build(new[] { contig3, contig2, contig1 }, pairedEndTags)).Return(new Scaffold());

			// Act
			_dnaAssembler.Assemble(new[] { contig1, contig2, contig3 }, pairedEndTags);

			// Assert
			_scaffoldBuilderMock.VerifyAllExpectations();
		}
	}
}
