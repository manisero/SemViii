using MBI.Logic.DNAAssemblance;
using MBI.Logic.DNAAssemblance._Impl;
using MBI.Logic.Serialization;
using MBI.Logic.Serialization._Impl;
using Ninject.Modules;

namespace MBI.UI.Bootstrap.Ninject
{
	public class LogicModule : NinjectModule
	{
		public override void Load()
		{
			Bind<IStreamHandler>().To<StreamHandler>();
			Bind<IAssemblyParser>().To<AssemblyParser>();

			Bind<IDNAAssembler>().To<DNAAssembler>();
			Bind<IScaffoldValidator>().To<ScaffoldValidator>();
			Bind<IScaffoldSerializer>().To<ScaffoldSerializer>();
		}
	}
}
