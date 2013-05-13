using MBI.Logic.Infrastructure;
using MBI.UI.Infrastructure;
using Ninject.Modules;

namespace MBI.UI.Bootstrap.Ninject
{
	public class InfrastructureModule : NinjectModule
	{
		public override void Load()
		{
			Bind<ISettingsProvider>().To<SettingsProvider>().InSingletonScope();
		}
	}
}
