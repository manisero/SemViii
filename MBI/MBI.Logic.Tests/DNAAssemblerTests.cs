using System.Collections.Generic;
using System.Threading;
using MBI.Logic.DNAAssemblance;
using MBI.Logic.DNAAssemblance._Impl;
using MBI.Logic.Entities;
using System.Linq;
using MBI.Logic.Infrastructure;
using NUnit.Framework;
using Rhino.Mocks;

namespace MBI.Logic.Tests
{
	[TestFixture]
	public class DNAAssemblerTests
	{
		private IContigsFilter _contigsFilterMock;
		private IScaffoldBuilder _scaffoldBuilderMock;
		private DNAAssembler _dnaAssembler;

		[SetUp]
		public void SetUp()
		{
			_contigsFilterMock = MockRepository.GenerateStrictMock<IContigsFilter>();
			_scaffoldBuilderMock = MockRepository.GenerateStrictMock<IScaffoldBuilder>();
			_dnaAssembler = new DNAAssembler(_contigsFilterMock, _scaffoldBuilderMock);
		}

		[Test]
		public void rejects_assemblies_of_rank_0()
		{
			// Arrange
			var contig1 = new Contig("aaa");
			var contig2 = new Contig("bbb");
			var initialContigs = new[] { contig1, contig2 };
			var contigs_accepted = new[] { contig1, contig2 };
			var contigs_rejected = new[] { contig2, contig1 };
			var pairedEndTags = new[] { new PairedEndTag() };

			var scaffold_accepted = new Scaffold { Rank = 10 };
			var scaffold_rejected = new Scaffold { Rank = 0 };

			var cancellationToken = new CancellationToken();

			_contigsFilterMock.Expect(x => x.Filter(initialContigs, pairedEndTags, cancellationToken)).Return(new[] { contigs_accepted, contigs_rejected });

			_scaffoldBuilderMock.Expect(x => x.Build(contigs_accepted, pairedEndTags, cancellationToken)).Return(scaffold_accepted);
			_scaffoldBuilderMock.Expect(x => x.Build(contigs_rejected, pairedEndTags, cancellationToken)).Return(scaffold_rejected);

			// Act
			var result = _dnaAssembler.Assemble(initialContigs, pairedEndTags, new ProgressIndication(null), cancellationToken).ToList();

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
			var contigs = new[] { contig1, contig2, contig3 };
			var pairedEndTags = new[] { new PairedEndTag() };

			var assembly1 = new[] { contig2, contig1, contig3 };
			var assembly2 = new[] { contig1, contig3, contig2 };
			var assembly3 = new[] { contig3, contig2, contig1 };

			var scaffold1 = new Scaffold { Rank = 3 };
			var scaffold2 = new Scaffold { Rank = 2 };
			var scaffold3 = new Scaffold { Rank = 1 };

			var cancellationToken = new CancellationToken();
			_contigsFilterMock.Expect(x => x.Filter(contigs, pairedEndTags, cancellationToken)).Return(new[] { assembly1, assembly2, assembly3 });

			_scaffoldBuilderMock.Expect(x => x.Build(assembly1, pairedEndTags, cancellationToken)).Return(scaffold1);
			_scaffoldBuilderMock.Expect(x => x.Build(assembly2, pairedEndTags, cancellationToken)).Return(scaffold2);
			_scaffoldBuilderMock.Expect(x => x.Build(assembly3, pairedEndTags, cancellationToken)).Return(scaffold3);
			
			// Act
			var result = _dnaAssembler.Assemble(contigs, pairedEndTags, new ProgressIndication(null), cancellationToken);

			// Assert
			Assert.IsNotNull(result);
			Assert.AreEqual(3, result.Count);
			Assert.AreEqual(scaffold1, result[0]);
			Assert.AreEqual(scaffold2, result[1]);
			Assert.AreEqual(scaffold3, result[2]);

			_scaffoldBuilderMock.VerifyAllExpectations();
		}
	}
}
