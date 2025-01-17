'use client'

import { useTheme } from '@/contexts/ThemeContext'
import { Moon, Sun } from 'lucide-react'
import { Button } from '@/components/ui/button'

export function ThemeToggle() {
  const { theme, toggleTheme } = useTheme()

  return (
    <Button 
      variant="outline" 
      size="icon" 
      onClick={toggleTheme}
      className="bg-card hover:bg-card-hover"
    >
      {theme === 'dark' ? (
        <Sun className="h-[1.2rem] w-[1.2rem] rotate-0 scale-100 transition-all text-text-primary" />
      ) : (
        <Moon className="h-[1.2rem] w-[1.2rem] rotate-0 scale-100 transition-all text-text-primary" />
      )}
      <span className="sr-only">Toggle theme</span>
    </Button>
  )
}

