using System.Windows;
using MBI.UI.Bootstrap.Ninject;
using MBI.UI.ViewModelLocation;
using MBI.UI.ViewModelLocation._Impl;

namespace MBI.UI
{
	/// <summary>
	/// Interaction logic for App.xaml
	/// </summary>
	public partial class App : Application
	{
		protected override void OnStartup(StartupEventArgs e)
		{
			base.OnStartup(e);

			var viewModelFactory = new NinjectViewModelFactory(new MBIKernel());
			((ViewModelLocator)Resources["Locator"]).ViewModelFactory = viewModelFactory;
		}
	}
}
