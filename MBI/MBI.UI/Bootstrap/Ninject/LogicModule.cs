using MBI.Logic.AssemblyParsing;
using MBI.Logic.AssemblyParsing._Impl;
using MBI.Logic.DNAAssemblance;
using MBI.Logic.DNAAssemblance._Impl;
using Ninject.Modules;

namespace MBI.UI.Bootstrap.Ninject
{
	public class LogicModule : NinjectModule
	{
		public override void Load()
		{
			Bind<IAssemblyParser>().To<AssemblyParser>();
			Bind<IDNAAssembler>().To<DNAAssembler>();
			Bind<IScaffoldValidator>().To<ScaffoldValidator>();
		}
	}
}
