using System.Windows;
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
			((ViewModelLocator)Resources["Locator"]).ViewModelFactory = new ActivatorBasedViewModelFactory();
		}
	}
}
