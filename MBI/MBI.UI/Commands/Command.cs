using System;
using System.Windows.Input;

namespace MBI.UI.Commands
{
	public class Command : ICommand
	{
		private readonly Action _action;

		public Command(Action action)
		{
			_action = action;
		}

		public void Execute(object parameter)
		{
			_action();
		}

		public bool CanExecute(object parameter)
		{
			return true;
		}

		public event EventHandler CanExecuteChanged;
	}
}
