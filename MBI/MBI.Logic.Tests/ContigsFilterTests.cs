using System.Collections.Generic;
using MBI.Logic.DNAAssemblance._Impl;
using MBI.Logic.Entities;
using MBI.Logic.Tests.Extensions;
using NUnit.Framework;
using System.Linq;

namespace MBI.Logic.Tests
{
	[TestFixture]
	public class ContigsFilterTests
	{
		[Test]
		public void rejects_contigs_in_wrong_order_based_on_single_PET()
		{
			// Arrange
			var contig1 = new Contig("aabbcc");
			var contig2 = new Contig("ddeeff");
			var contig3 = new Contig("gghhii");
			var pairedEndTags = new[] { new PairedEndTag { Beginning = "bb", End = "hh" } };

			var expecetedResult = new[]
			                         	{
			                         		new List<Contig> { contig1, contig2, contig3 },
											new List<Contig> { contig1, contig3, contig2 },
											new List<Contig> { contig2, contig1, contig3 }
			                         	};

			// Act
			var result = new ContigsFilter().Filter(new[] { contig1, contig2, contig3 }, pairedEndTags);

			// Assert
			Assert.IsNotNull(result);
			AssertExtensions.AreEqual(result, expecetedResult);
		}

		[Test]
		public void rejects_combinations_based_on_first_matched_PET()
		{
			// Arrange
			var contig1 = new Contig("aabbcc");
			var contig2 = new Contig("ddeeff");
			var contig3 = new Contig("gghhii");
			var pairedEndTags = new[]
			                    	{
			                    		new PairedEndTag { Beginning = "aa", End = "yy" },
										new PairedEndTag { Beginning = "xx", End = "ee" },
										new PairedEndTag { Beginning = "bb", End = "hh" }
			                    	};

			var expectedResult = new[]
			                         	{
			                         		new List<Contig> { contig1, contig2, contig3 },
											new List<Contig> { contig1, contig3, contig2 },
											new List<Contig> { contig2, contig1, contig3 }
			                         	};

			// Act
			var result = new ContigsFilter().Filter(new[] { contig1, contig2, contig3 }, pairedEndTags);

			// Assert
			Assert.IsNotNull(result);
			AssertExtensions.AreEqual(result, expectedResult);
		}

		[Test]
		public void accepts_all_combinations_if_no_PET_matched()
		{
			// Arrange
			var contig1 = new Contig("aabbcc");
			var contig2 = new Contig("ddeeff");
			var pairedEndTags = new[]
			                    	{
			                    		new PairedEndTag { Beginning = "aa", End = "yy" },
										new PairedEndTag { Beginning = "xx", End = "ee" }
			                    	};

			var expectedResult = new[]
			                         	{
											new List<Contig> { contig1, contig2 },
											new List<Contig> { contig2, contig1 }
			                         	};

			// Act
			var result = new ContigsFilter().Filter(new[] { contig1, contig2 }, pairedEndTags);

			// Assert
			Assert.IsNotNull(result);
			AssertExtensions.AreEqual(result, expectedResult);
		}
	}
}
