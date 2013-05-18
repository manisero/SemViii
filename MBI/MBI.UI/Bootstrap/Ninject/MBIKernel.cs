using Ninject;

namespace MBI.UI.Bootstrap.Ninject
{
	public class MBIKernel : StandardKernel
	{
		public MBIKernel() : base(new InfrastructureModule(), new LogicModule())
		{
			
		}
	}
}
