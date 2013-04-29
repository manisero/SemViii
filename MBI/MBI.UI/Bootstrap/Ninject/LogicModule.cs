using MBI.Logic.DNAAssemblance;
using MBI.Logic.DNAAssemblance._Impl;
using Ninject.Modules;

namespace MBI.UI.Bootstrap.Ninject
{
	public class LogicModule : NinjectModule
	{
		public override void Load()
		{
			Bind<IDNAAssembler>().To<DNAAssembler>();
			Bind<IScaffoldValidator>().To<ScaffoldValidator>();
		}
	}
}
