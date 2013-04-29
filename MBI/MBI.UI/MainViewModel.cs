using System.Windows;
using System.Windows.Input;
using MBI.UI.Commands;

namespace MBI.UI
{
	public class MainViewModel
	{
		private readonly ICommand _chooseFile = new Command(() => MessageBox.Show("foo"));

		public ICommand ChooseFile
		{
			get { return _chooseFile; }
		}

		private readonly ICommand _assemble = new Command(() => MessageBox.Show("bar"));

		public ICommand Assemble
		{
			get { return _assemble; }
		}
	}
}
