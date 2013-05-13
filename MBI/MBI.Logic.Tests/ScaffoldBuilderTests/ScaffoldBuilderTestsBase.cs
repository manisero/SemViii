using System.Collections.Generic;
using MBI.Logic.DNAAssemblance._Impl;
using MBI.Logic.Entities;
using MBI.Logic.Infrastructure;
using MBI.Logic.Tests.Extensions;
using NUnit.Framework;
using System.Linq;
using Rhino.Mocks;

namespace MBI.Logic.Tests.ScaffoldBuilderTests
{
	public class ScaffoldBuilderTestsBase
	{
		protected ISettingsProvider SettingsProviderMock;
		private ScaffoldBuilder _scaffoldBuilder;

		[SetUp]
		public virtual void SetUp()
		{
			SettingsProviderMock = MockRepository.GenerateStrictMock<ISettingsProvider>();
			_scaffoldBuilder = new ScaffoldBuilder(SettingsProviderMock);

			SettingsProviderMock.Stub(x => x.PartialMatchMinPercentage).Return(0.0);
		}

		protected void TestRank(IEnumerable<string> contigs, PairedEndTag pet, int expectedRank)
		{
			TestRank(contigs, new[] { pet }, expectedRank);
		}

		protected void TestRank(IEnumerable<string> contigs, PairedEndTag[] pets, int expectedRank)
		{
			// Act
			var result = _scaffoldBuilder.Build(contigs.Select(x => new Contig(x)).ToArray(), pets);

			// Assert
			Assert.IsNotNull(result);
			Assert.AreEqual(expectedRank, result.Rank);
		}

		protected void TestBuild(Scaffold expectedScaffold, params PairedEndTag[] pets)
		{
			// Act
			var result = _scaffoldBuilder.Build(expectedScaffold.Pieces.OfType<Contig>().ToArray(), pets);

			// Assert
			Assert.IsNotNull(result);
			Assert.AreEqual(expectedScaffold.Rank, result.Rank);
			AssertExtensions.AreEqual(expectedScaffold.Pieces, result.Pieces, (x, y) => Assert.AreEqual(x.Content, y.Content));
		}
	}
}
