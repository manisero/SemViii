using AutoMoq;
using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace MBI.Logic.Tests
{
	[TestClass]
	public abstract class TestsBase
	{
		protected AutoMoqer AutoMoqer { get; private set; }

		[TestInitialize]
		public void Setup()
		{
			AutoMoqer = new AutoMoqer();
		}
	}
}
